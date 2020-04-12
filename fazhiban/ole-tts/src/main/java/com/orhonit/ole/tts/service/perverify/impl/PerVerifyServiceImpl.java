package com.orhonit.ole.tts.service.perverify.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.CaseDao;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.PerVerifyInfoDTO;
import com.orhonit.ole.tts.entity.PartyInfoEntity;
import com.orhonit.ole.tts.repository.CaseRepository;
import com.orhonit.ole.tts.repository.PartyInfoRepository;
import com.orhonit.ole.tts.service.flow.FlowService;
import com.orhonit.ole.tts.service.perverify.PerVerifyService;
import com.orhonit.ole.tts.utils.EnforceException;

@Service
public class PerVerifyServiceImpl implements PerVerifyService{
	
	@Autowired
	private CaseDao caseDao;
	
	@Autowired
	private PartyInfoRepository partyInfoRepository;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private CaseRepository caseRepository;

	@Override
	public Integer getCasecount(Map<String, Object> params) {
		return this.caseDao.count(params);
	}

	@Override
	public List<CaseListDTO> getCaseList(Map<String, Object> params, Integer start, Integer length) {
		return this.caseDao.caseList(params, start, length);
	}

	@Override
	@Transactional
	public void savePerVerify(PerVerifyInfoDTO perVerifyInfoDTO) {
		
		PartyInfoEntity partyInfoEntity = this.partyInfoRepository.findByCaseId(perVerifyInfoDTO.getCaseId());

		User user = UserUtil.getCurrentUser();
		
		if ( partyInfoEntity == null ) {
			// 新增
			partyInfoEntity = new PartyInfoEntity();
			BeanUtils.copyProperties(perVerifyInfoDTO, partyInfoEntity);
			partyInfoEntity.setCreateBy(user.getUsername());
			partyInfoEntity.setCreateName(user.getNickname());
			partyInfoEntity.setCreateDate(new Date());
			partyInfoEntity.setId(UUID.randomUUID().toString());
			this.partyInfoRepository.save(partyInfoEntity);
		} else {
			// 更新
			partyInfoEntity.setAddress(perVerifyInfoDTO.getAddress());
			partyInfoEntity.setAge(perVerifyInfoDTO.getAge());
			partyInfoEntity.setIdCard(perVerifyInfoDTO.getIdCard());
			partyInfoEntity.setLegalName(perVerifyInfoDTO.getLegalName());
			partyInfoEntity.setName(perVerifyInfoDTO.getName());
			partyInfoEntity.setOrgCode(perVerifyInfoDTO.getOrgCode());
			partyInfoEntity.setOrgIdCard(perVerifyInfoDTO.getOrgIdCard());
			partyInfoEntity.setPostCode(perVerifyInfoDTO.getPostCode());
			partyInfoEntity.setSex(perVerifyInfoDTO.getSex());
			partyInfoEntity.setTel(perVerifyInfoDTO.getTel());
			partyInfoEntity.setType(perVerifyInfoDTO.getType());
			partyInfoEntity.setUnitAddress(perVerifyInfoDTO.getUnitAddress());
			partyInfoEntity.setUnitName(perVerifyInfoDTO.getUnitName());
			partyInfoEntity.setUpdateBy(user.getUsername());
			partyInfoEntity.setUpdateName(user.getNickname());
			partyInfoEntity.setUpdateDate(new Date());
			this.partyInfoRepository.save(partyInfoEntity);
		}
		if ( perVerifyInfoDTO.getPerVerifyStatus().intValue() == 0 ) {
			// 暂存, 不作处理
		} else if ( perVerifyInfoDTO.getPerVerifyStatus().intValue() == CommonParameters.CaseStatus.CBHS ) {
			// 完成自己的任务
			if ( perVerifyInfoDTO.getDealType().intValue() == CommonParameters.SimpleFlow.NOT_DEAL) {
				
				// this.flowService.taskComplete(pid, createName, variables, comment);
				
				String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId("case", caseRepository.findOne(perVerifyInfoDTO.getCaseId()).getCaseNum());
				Map<String, Object> variables = new HashMap<String,Object>();
				variables.put("handleMode", CommonParameters.SimpleFlow.NOT_DEAL );
				this.flowService.taskComplete(pid, user.getNickname(), variables, perVerifyInfoDTO.getComment());
				this.caseDao.updateCaseStatus(perVerifyInfoDTO.getCaseId(), CommonParameters.CaseStatus.ANGD);
			} else if ( perVerifyInfoDTO.getDealType().intValue() == CommonParameters.SimpleFlow.LOCATE_PRO) {
				// 现场处理
				String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId("case", caseRepository.findOne(perVerifyInfoDTO.getCaseId()).getCaseNum());
				Map<String, Object> variables = new HashMap<String,Object>();
				variables.put("handleMode", CommonParameters.SimpleFlow.LOCATE_PRO );
				this.flowService.taskComplete(pid, user.getNickname(), variables, perVerifyInfoDTO.getComment());
				this.caseDao.updateCaseStatus(perVerifyInfoDTO.getCaseId(), CommonParameters.CaseStatus.XCCL);
			}
			
		} else {
			throw new EnforceException(ResultCode.BUSI_ERROR.getCode(), "the status is error.");
		}
		
	}
	
	@Override
	public Boolean haveParty(String caseNum){
		String caseId = caseRepository.findByCaseNum(caseNum).getId();
		PartyInfoEntity partyInfoEntity = partyInfoRepository.findByCaseId(caseId);
		if(partyInfoEntity!=null){
			return true;
		}
		return false;
	}
}
