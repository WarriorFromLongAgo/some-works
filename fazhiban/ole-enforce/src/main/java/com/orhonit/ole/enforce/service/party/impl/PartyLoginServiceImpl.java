package com.orhonit.ole.enforce.service.party.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dto.CaseInfoDTO;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.enforce.entity.PartyLoginEntity;
import com.orhonit.ole.enforce.entity.SmsEntity;
import com.orhonit.ole.enforce.repository.PartyLoginRepository;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.party.PartyLoginService;
import com.orhonit.ole.enforce.service.sms.SmsService;
import com.orhonit.ole.enforce.utils.MD5Utils;
import com.orhonit.ole.enforce.utils.SmsSend;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@Service
public class PartyLoginServiceImpl implements PartyLoginService {

	@Autowired
	private PartyLoginRepository partyLoginRepository;

	@Autowired
	private FlowService flowService;

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private CaseService caseService;

	@Override
	public void savePartyLogin(PartyLoginEntity partyLoginEntity) {
		partyLoginEntity.setLoginPassword(MD5Utils.MD5(partyLoginEntity.getLoginPassword()));
		this.partyLoginRepository.save(partyLoginEntity);
	}

	@Override
	public PartyLoginEntity findPartyLoginByLoginName(String loginName) {
		return this.partyLoginRepository.findOne(loginName);
	}

	@Override
	public Boolean createPartyLogin(String caseId, String hearingDate) {
		try {
			User user = UserUtil.getCurrentUser();
			PartyInfoEntity partyInfoEntity = flowService.getPartyByCaseId(caseId);
			PartyLoginEntity partyLoginEntity = new PartyLoginEntity();

			partyLoginEntity.setPersonType(partyInfoEntity.getType());
			if (partyLoginEntity.getPersonType() == 1) {
				partyLoginEntity.setLoginName(partyInfoEntity.getIdCard());
			} else {
				partyLoginEntity.setLoginName(partyInfoEntity.getOrgCode());
			}
			String pass = String.valueOf(Math.random());
			// 用户已存在时修改密码
			if (this.findPartyLoginByLoginName(partyLoginEntity.getLoginName()) != null) {
				pass = pass.substring(2, 8);
				partyLoginEntity.setLoginPassword(pass);
				partyLoginEntity.setUpdateDate(new Date());
				partyLoginEntity.setUpdateBy(user.getUsername());
				partyLoginEntity.setUpdateName(user.getNickname());
			}else{
				pass = pass.substring(2, 8);
				partyLoginEntity.setLoginPassword(pass);
				partyLoginEntity.setCreateDate(new Date());
				partyLoginEntity.setCreateBy(user.getUsername());
				partyLoginEntity.setCreateName(user.getNickname());
			}

			this.savePartyLogin(partyLoginEntity);
			// 添加到短信表
			SmsEntity smsEntity = new SmsEntity();
			SmsEntity smsEntitynm = new SmsEntity();
			smsEntity.setCreateBy(user.getUsername());
			smsEntity.setCreateDate(new Date());
			smsEntity.setCreateName(user.getNickname());
			smsEntity.setTelNum(partyInfoEntity.getTel());
			smsEntitynm.setCreateBy(user.getUsername());
			smsEntitynm.setCreateDate(new Date());
			smsEntitynm.setCreateName(user.getNickname());
			smsEntitynm.setTelNum(partyInfoEntity.getTel());
			
			CaseInfoDTO caseInfoDTO = caseService.findOne(caseId);
			String caseStatus=null;
			String caseStatusNo = null;
			String caseStatusnm=null;
			String caseStatusNonm = null;
			if(caseInfoDTO.getCaseStatus() == 34){
				caseStatus="申请听证";
				caseStatusNo="申请案件处理";
				caseStatusnm="ᠭᠡᠷᠡᠴᠢᠯᠡᠯᠲᠡ ᠰᠣᠨᠤᠰᠬᠤ ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ";
				caseStatusNonm="ᠬᠡᠷᠡᠭ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠬᠦ  ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ";
			}else if(caseInfoDTO.getCaseStatus() == 24){
				caseStatus="申请陈述申辩";
				caseStatusNo="申请案件处理";
				caseStatusnm="ᠲᠣᠭᠠᠴᠢᠯᠲᠠ ᠥᠮᠥᠭᠯᠡᠬᠦ ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ";
				caseStatusNonm="ᠬᠡᠷᠡᠭ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠬᠦ  ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ";
			}else if(caseInfoDTO.getCaseStatus() == 56){
				caseStatus="申请复议";
				caseStatusNo="结案申请";
				caseStatusnm="ᠳᠠᠬᠢᠨ ᠵᠥᠪᠯᠡᠬᠦ  ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ";
				caseStatusNonm="ᠬᠡᠷᠡᠭ ᠳᠠᠭᠤᠰᠬᠠᠬᠤ ᠶᠢ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ";
			}
			String content = "：您好，您案件编号为【"+caseInfoDTO.getCaseNum()+"】的案件已经可以【"+caseStatus+"】了，请您在【"+hearingDate+"】日内进行申请，逾期将自动进行【"+caseStatusNo+"】,请及时登录【呼和浩特市行政执法监督平台】进行处理，登录帐号：" + partyLoginEntity.getLoginName() + "密码：" + pass;
			String contentnm = "ᠰᠠᠶᠢᠨ ᠤᠤ , ᠲᠠᠨ ᠦ ᠬᠡᠷᠡᠭ ᠦᠨ ᠳ᠋ᠤᠭᠠᠷᠯᠠᠯ ᠨᠢ 【"+caseInfoDTO.getCaseNum()+"】 ᠪᠣᠯᠬᠤ ᠬᠡᠷᠡᠭ ᠨᠢᠭᠡᠨᠲᠡ  【"+caseStatusnm+"】 ᠪᠣᠯᠤᠨ᠎ᠠ ᠃ ᠲᠠ 【"+hearingDate+"】 ᠡᠳᠦᠷ ᠦᠨ ᠳᠣᠲᠣᠷ᠎ᠠ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ ᠬᠡᠷᠡᠭᠲᠡᠢ ᠂ ᠬᠤᠭᠤᠴᠠᠭ᠎ᠠ ᠭᠡᠲᠦᠷᠡᠪᠡᠯ  【"+caseStatusNonm+"】 ᠲᠥᠷᠦᠯ  ᠳᠦ ᠥᠪᠡᠷᠲᠡᠭᠡᠨ ᠣᠷᠤᠨ᠎ᠠ ᠃︽ ᠬᠥᠬᠡᠬᠣᠲᠠ ᠶᠢᠨ ᠵᠠᠰᠠᠭ ᠵᠠᠬᠢᠷᠭᠠᠨ ᠤ ᠬᠠᠤᠯᠢ ᠬᠡᠷᠡᠭᠵᠢᠭᠦᠯᠬᠦ ᠬᠢᠨᠠᠯᠲᠠ ᠲᠠᠯᠪᠢᠬᠤ ᠲᠠᠪᠴᠠᠩ︾ ᠳᠦ ᠨᠡᠪᠲᠡᠷᠡᠵᠦ ᠴᠠᠭ ᠲᠤᠬᠠᠢ ᠳ᠋ᠤᠨᠢ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠭᠡᠷᠡᠢ ！ ᠨᠡᠪᠲᠡᠷᠡᠬᠦ ᠳ᠋ᠤᠭᠠᠷ᠄ " + partyLoginEntity.getLoginName() + " ᠂ ᠨᠢᠭᠤᠴᠠ ᠳ᠋ᠤᠭᠠᠷ᠄ " + pass;
			smsEntity.setContent(content);
			smsEntitynm.setContent(contentnm);
			boolean falg=false;
			try {
			   //falg=SmsSend.sendMsg(smsEntity,"71134");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(falg){
					smsEntity.setStatus("1");
				}else{
					smsEntity.setStatus("0");
				}
				this.smsService.saveSms(smsEntity);
			}
			falg=false;
			try {
			   //falg=SmsSend.sendMsg(smsEntitynm,"71134");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(falg){
					smsEntitynm.setStatus("1");
				}else{
					smsEntitynm.setStatus("0");
				}
				this.smsService.saveSms(smsEntitynm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
//	ᠨᠡᠪᠲᠡᠷᠡᠬᠦ ᠳ᠋ᠤᠭᠠᠷ 登陆账号
//	ᠨᠡᠪᠲᠡᠷᠡᠬᠦ ᠬᠦᠮᠦᠨ 登陆人
//	ᠨᠡᠪᠲᠡᠷᠡᠬᠦ ᠲᠥᠷᠦᠯ ᠵᠦᠢᠯ 登陆类型
//	ᠭᠡᠷᠡᠴᠢᠯᠡᠯᠲᠡ ᠰᠣᠨᠤᠰᠬᠤ ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ 申请听证
//	ᠬᠡᠷᠡᠭ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠬᠦ  ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ 申请案件处理
//	ᠲᠣᠭᠠᠴᠢᠯᠲᠠ ᠥᠮᠥᠭᠯᠡᠬᠦ ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ  申请陈述申辩
//	ᠬᠡᠷᠡᠭ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠬᠦ  ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ  申请案件处理
//	ᠳᠠᠬᠢᠨ ᠵᠥᠪᠯᠡᠬᠦ  ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ   申请复议
//	ᠬᠡᠷᠡᠭ ᠳᠠᠭᠤᠰᠬᠠᠬᠤ ᠶᠢ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ  结案申请
//	[ ᠬᠥᠬᠡᠬᠣᠲᠠ  ᠶᠢᠨ ᠬᠠᠤᠯᠢ ᠳᠦᠷᠢᠮ ᠦᠨ ᠠᠵᠢᠯ ᠤᠨ ᠠᠯᠪᠠᠨ ᠭᠡᠷ ] ᠬᠥᠬᠡᠬᠣᠲᠠ ᠶᠢᠨ ᠵᠠᠰᠠᠭ ᠵᠠᠬᠢᠷᠭᠠᠨ ᠤ ᠬᠠᠤᠯᠢ ᠬᠡᠷᠡᠭᠵᠢᠭᠦᠯᠬᠦ ᠬᠢᠨᠠᠯᠲᠠ ᠲᠠᠯᠪᠢᠬᠤ ᠲᠠᠪᠴᠠᠩ ᠳᠦ ᠄ ᠰᠠᠶᠢᠨ ᠤᠤ ？ ᠲᠠᠨ ᠦ ᠬᠡᠷᠡᠭ ᠦᠨ ᠳ᠋ᠤᠭᠠᠷᠯᠠᠯ ᠨᠢ [CF--20180304-0002] ᠪᠣᠯᠬᠤ ᠬᠡᠷᠡᠭ ᠨᠢᠭᠡᠨᠲᠡ  ᠲᠣᠭᠠᠴᠢᠯᠲᠠ ᠥᠮᠥᠭᠯᠡᠬᠦ ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠵᠤ ᠪᠣᠯᠤᠨ᠎ᠠ ᠃ ᠲᠠ 3 ᠡᠳᠦᠷ ᠦᠨ ᠳᠣᠲᠣᠷ᠎ᠠ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ ᠬᠡᠷᠡᠭᠲᠡᠢ ᠂ ᠬᠤᠭᠤᠴᠠᠭ᠎ᠠ ᠭᠡᠲᠦᠷᠡᠪᠡᠯ  [ ᠬᠡᠷᠡᠭ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠬᠦ ᠪᠡᠷ ᠭᠤᠶᠤᠴᠢᠯᠠᠬᠤ] ᠲᠥᠷᠦᠯ  ᠳᠦ ᠥᠪᠡᠷᠲᠡᠭᠡᠨ ᠣᠷᠤᠨ᠎ᠠ ᠃︽ ᠬᠥᠬᠡᠬᠣᠲᠠ ᠶᠢᠨ ᠵᠠᠰᠠᠭ ᠵᠠᠬᠢᠷᠭᠠᠨ ᠤ ᠬᠠᠤᠯᠢ ᠬᠡᠷᠡᠭᠵᠢᠭᠦᠯᠬᠦ ᠬᠢᠨᠠᠯᠲᠠ ᠲᠠᠯᠪᠢᠬᠤ ᠲᠠᠪᠴᠠᠩ︾ ᠳᠦ ᠨᠡᠪᠲᠡᠷᠡᠵᠦ ᠴᠠᠭ ᠲᠤᠬᠠᠢ ᠳ᠋ᠤᠨᠢ ᠰᠢᠢᠳᠪᠦᠷᠢᠯᠡᠭᠡᠷᠡᠢ ！ ᠨᠡᠪᠲᠡᠷᠡᠬᠦ ᠳ᠋ᠤᠭᠠᠷ᠄ 150100102682001102 ᠂ ᠨᠢᠭᠤᠴᠠ ᠳ᠋ᠤᠭᠠᠷ᠄ 249182
//	【呼和浩特法制办】呼和浩特市行政执法监督平台：您好，您案件编号为【CF--20180304-0002】的案件已经可以【申请陈述申辩】了，请您在【3】日内进行申请，逾期将自动进行【申请案件处理】,请及时登录【呼和浩特市行政执法监督平台】进行处理，登录帐号：150100102682001102密码：249182
//	public static void main(String[] args) {
//		SmsEntity smsEntity = new SmsEntity();
//		smsEntity.setTelNum("18004890929");
//		smsEntity.setTelNum("17777784272");
//		String content = "：ᠬᠡᠷᠡᠭᠯᠡᠭᠴᠢ ᠶᠢᠨ ᠨᠡᠷ᠎ᠡ ᠪᠤᠶᠤ ᠨᠢᠭᠤᠴᠠ ᠦᠭᠡ ᠪᠤᠷᠤᠭᠤᠳᠠᠭᠰᠠᠨᠬᠡᠷᠡᠭᠯᠡᠭᠴᠢ ᠶᠢᠨ ᠨᠡᠷ᠎ᠡ ᠪᠤᠶᠤ ᠨᠢᠭᠤᠴᠠ ᠦᠭᠡ ᠪᠤᠷᠤᠭᠤᠳᠠᠭᠰᠠᠨᠬᠡᠷᠡᠭᠯᠡᠭᠴᠢ ᠶᠢᠨ ᠨᠡᠷ᠎ᠡ ᠪᠤᠶᠤ ᠨᠢᠭᠤᠴᠠ ᠦᠭᠡ ᠪᠤᠷᠤᠭᠤᠳᠠᠭᠰᠠᠨ";
//		System.out.println(content);
//		smsEntity.setContent(content);
//		boolean falg=false;
//		try {
//		   falg=SmsSend.sendMsg(smsEntity,"71134");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			if(falg){
//				smsEntity.setStatus("1");
//			}else{
//				smsEntity.setStatus("0");
//			}
//			System.out.println(smsEntity);
//		}
//	}

}
