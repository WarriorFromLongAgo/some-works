package com.orhonit.ole.tts.service.lssued.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.sys.model.SequenceEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.SequenceRepository;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.config.ThreadLocalVariables;
import com.orhonit.ole.tts.dao.LssuedDao;
import com.orhonit.ole.tts.dao.PersonDao;
import com.orhonit.ole.tts.dto.CheckDocDTO;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.LssuedDTO;
import com.orhonit.ole.tts.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.tts.dto.PersonDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseListDTO;
import com.orhonit.ole.tts.entity.CheckRecordEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.entity.LssuedPersonEntity;
import com.orhonit.ole.tts.repository.CheckRecordRepository;
import com.orhonit.ole.tts.repository.DeptRepository;
import com.orhonit.ole.tts.service.flow.FlowService;
import com.orhonit.ole.tts.service.lssued.LssuedService;
import com.orhonit.ole.tts.utils.EnforceException;

@Service
public class LssuedServiceImpl implements LssuedService{
	
	private static final String PRO_NUM_TITLE = "ZXJC";
	
	private static final String PRO_NUM_PREFIX = "-";
	
	private static final String PRO_NUM_SEQ_NAME = "pro_id_seq";
	
	private static final int PRO_NUM_PAD_NUM = 4;
	
	@Autowired
	private LssuedDao lssuedDao;
	
	@Autowired
	private SequenceRepository sequenceRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private FlowService flowService;

	@Autowired
	private CheckRecordRepository checkRecordRepository;
	
	@Autowired
	private PersonDao personDao;
	
	
	@Override
	@Transactional
	public List<FlowDTO> save(LssuedEntity lssuedEntity) {
		
		if(lssuedEntity.getId()==null || lssuedEntity.getId().equals("")){
			String perS =lssuedEntity.getPersonId();
			System.out.println(perS);
			for(String per : perS.split(",")){
				User user = UserUtil.getCurrentUser();
				lssuedEntity.setCreateDate(new Date());
				lssuedEntity.setCreateBy(user.getNickname());
				lssuedEntity.setCreateName(user.getUsername());
				lssuedEntity.setId(UUID.randomUUID().toString());
				// 案件编号,生成规则(处罚-机构-日期-4位序列  )  e.g. CF-15010001003-20171130-0003
				lssuedEntity.setCheckNum(this.getCheckNum());
				lssuedEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
				lssuedEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
				LssuedEntity lssuedEntitycopy= new LssuedEntity();
				BeanUtils.copyProperties(lssuedEntity,lssuedEntitycopy);
				lssuedEntitycopy.setPersonId(per);
				lssuedDao.save(lssuedEntitycopy);
				saveLssuedPerson(lssuedEntitycopy);
				
				System.out.println(lssuedEntitycopy.getComment());
				// 根据状态判断是否需要启动流程
				if ( lssuedEntity.getStatus().equals(CommonParameters.CheckStatus.LOCATE_CHECK.toString())) {
					FlowDTO flowDTO = new FlowDTO();	
					PersonDTO usId=lssuedDao.findUserId(user.getPerson_id());
					String assignee=usId.getId();
					flowDTO.setAssignee(assignee);
					flowDTO.setBusinessId(lssuedEntity.getCheckNum());
					flowDTO.setComment(lssuedEntity.getComment());
					
					PersonDTO personId=lssuedDao.findUserId(per);
					String nextAssignee=personId.getId();
		
					flowDTO.setNextAssignee(nextAssignee);
					flowDTO.setFlowKey(CommonParameters.FlowKey.PRO_CHECK);
					this.flowService.startFlowByKey(flowDTO);
				}
				
			}
		}else{
			String perS =lssuedEntity.getPersonId();
			System.out.println(perS);
			int  flag=0;
			for(String per : perS.split(",")){
					if(flag!=0){
						lssuedEntity.setId(UUID.randomUUID().toString());
						lssuedEntity.setCheckNum(this.getCheckNum());
					}
					User user = UserUtil.getCurrentUser();
					lssuedEntity.setCreateDate(new Date());
					lssuedEntity.setCreateBy(user.getNickname());
					lssuedEntity.setCreateName(user.getUsername());
					lssuedEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
					lssuedEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
					LssuedEntity lssuedEntitycopy= new LssuedEntity();
					BeanUtils.copyProperties(lssuedEntity,lssuedEntitycopy);
					lssuedEntitycopy.setPersonId(per);
					if(flag!=0){
						lssuedDao.save(lssuedEntitycopy);
					}else{
						lssuedDao.update(lssuedEntitycopy);
					}
					lssuedDao.del(lssuedEntitycopy.getId());
					saveLssuedPerson(lssuedEntitycopy);
					
					System.out.println(lssuedEntitycopy.getComment());
					// 根据状态判断是否需要启动流程
					if ( lssuedEntity.getStatus().equals(CommonParameters.CheckStatus.LOCATE_CHECK.toString())) {
						FlowDTO flowDTO = new FlowDTO();	
						PersonDTO usId=lssuedDao.findUserId(user.getPerson_id());
						String assignee=usId.getId();
						flowDTO.setAssignee(assignee);
						flowDTO.setBusinessId(lssuedEntity.getCheckNum());
						flowDTO.setComment(lssuedEntity.getComment());
						
						PersonDTO personId=lssuedDao.findUserId(per);
						String nextAssignee=personId.getId();
			
						flowDTO.setNextAssignee(nextAssignee);
						flowDTO.setFlowKey(CommonParameters.FlowKey.PRO_CHECK);
						this.flowService.startFlowByKey(flowDTO);
					}
					flag+=1;
				}
		}
		return null;
	}

	private void saveLssuedPerson(LssuedEntity lssuedEntity) {
		LssuedPersonEntity lssuedPersonEntity = new LssuedPersonEntity() ;
		
		if(lssuedPersonEntity.getId()==null || lssuedPersonEntity.getId().equals("")){
			lssuedPersonEntity.setId(UUID.randomUUID().toString());
		}
		lssuedPersonEntity.setCheck_id(lssuedEntity.getId());
		lssuedPersonEntity.setDept_id(lssuedEntity.getDeptId());
		lssuedPersonEntity.setPerson_id(lssuedEntity.getPersonId());
		lssuedPersonEntity.setCreateDate(lssuedEntity.getCreateDate());
		lssuedPersonEntity.setCreateBy(lssuedEntity.getCreateBy());
		lssuedPersonEntity.setCreateName(lssuedEntity.getCreateName());
		lssuedDao.savePerson(lssuedPersonEntity);
	}
	@Override
	public Integer getCasecount(Map<String, Object> params) {
		return this.lssuedDao.count(params);
	}

	@Override
	public List<LssuedEntity> getCaseList(Map<String, Object> params, Integer start, Integer length) {
		return this.lssuedDao.list(params, start, length);
	}

	@Override
	public void updateCaseStatusByCaseNum(String businessKey, Integer valueOf) {
		this.lssuedDao.updateCaseStatusByCaseNum(businessKey, valueOf);
		
	}
	
	/**
	 * APP获取案件编号
	 * CF-机构代码-日期-0001
	 * @return
	 */
	private String getCheckNumForApp() {
		
		SequenceEntity sequenceEntity = this.sequenceRepository.findOne(PRO_NUM_SEQ_NAME);
		if ( sequenceEntity == null || sequenceEntity.getSeqDate() == null) {
			throw new EnforceException(ResultCode.BUSI_ERROR.getCode(), PRO_NUM_SEQ_NAME + ", seq_name is not exits or seq_date is null.");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar now = Calendar.getInstance();
		
		StringBuilder builder = new StringBuilder();
		//当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user =this.personDao.findPersonInfo(personDTO.getId());
		String deptCode = this.deptRepository.findOne(user.getDeptId()).getCode();
		
		builder.append(PRO_NUM_TITLE)
			.append(PRO_NUM_PREFIX)
			.append(deptCode)
			.append(PRO_NUM_PREFIX)
			.append(sdf.format(now.getTime()))
			.append(PRO_NUM_PREFIX);
		
		Calendar seqDate = Calendar.getInstance();
		
		seqDate.setTime(sequenceEntity.getSeqDate());
		
		if ( now.get(Calendar.YEAR) == seqDate.get(Calendar.YEAR) 
				&& now.get(Calendar.MONTH) == seqDate.get(Calendar.MONTH)
				&& now.get(Calendar.DAY_OF_MONTH) == seqDate.get(Calendar.DAY_OF_MONTH)) {
			
			Integer nextVal = Integer.valueOf(sequenceEntity.getSeqValue()) + 1;
			sequenceEntity.setSeqValue(String.valueOf(nextVal));
			builder.append(
					String.format("%" + PRO_NUM_PAD_NUM +"s", sequenceEntity.getSeqValue()).replace(' ', '0'));
			
		} else {
			builder.append(
					String.format("%" + PRO_NUM_PAD_NUM +"s", 1).replace(' ', '0'));
			// 更新序列
			sequenceEntity.setSeqValue("1");
			sequenceEntity.setSeqDate(now.getTime());
		}
		this.sequenceRepository.save(sequenceEntity);
		return builder.toString();
	}
	
	/**
	 * 获取案件编号
	 * CF-机构代码-日期-0001
	 * @return
	 */
	private String getCheckNum() {
		
		SequenceEntity sequenceEntity = this.sequenceRepository.findOne(PRO_NUM_SEQ_NAME);
		if ( sequenceEntity == null || sequenceEntity.getSeqDate() == null) {
			throw new EnforceException(ResultCode.BUSI_ERROR.getCode(), PRO_NUM_SEQ_NAME + ", seq_name is not exits or seq_date is null.");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar now = Calendar.getInstance();
		
		StringBuilder builder = new StringBuilder();
		
		String deptCode = this.deptRepository.findOne(UserUtil.getCurrentUser().getDept_id()).getCode();
		
		builder.append(PRO_NUM_TITLE)
			.append(PRO_NUM_PREFIX)
			.append(deptCode)
			.append(PRO_NUM_PREFIX)
			.append(sdf.format(now.getTime()))
			.append(PRO_NUM_PREFIX);
		
		Calendar seqDate = Calendar.getInstance();
		
		seqDate.setTime(sequenceEntity.getSeqDate());
		
		if ( now.get(Calendar.YEAR) == seqDate.get(Calendar.YEAR) 
				&& now.get(Calendar.MONTH) == seqDate.get(Calendar.MONTH)
				&& now.get(Calendar.DAY_OF_MONTH) == seqDate.get(Calendar.DAY_OF_MONTH)) {
			
			Integer nextVal = Integer.valueOf(sequenceEntity.getSeqValue()) + 1;
			sequenceEntity.setSeqValue(String.valueOf(nextVal));
			builder.append(
					String.format("%" + PRO_NUM_PAD_NUM +"s", sequenceEntity.getSeqValue()).replace(' ', '0'));
			
		} else {
			builder.append(
					String.format("%" + PRO_NUM_PAD_NUM +"s", 1).replace(' ', '0'));
			// 更新序列
			sequenceEntity.setSeqValue("1");
			sequenceEntity.setSeqDate(now.getTime());
		}
		this.sequenceRepository.save(sequenceEntity);
		return builder.toString();
	}

	@Override
	public List<ApiCaseListDTO> findLssuedList(Map<String, Object> params) {	
		return this.lssuedDao.findLssuedList(params);
	}

	@Override
	public LssuedDetailInfoDTO findCaseInfo(Map<String, Object> params) {
		return this.lssuedDao.findCaseInfo(params);
	}

	@Override
	public LssuedEntity findOne(String id) {
		LssuedEntity lssuedEntity = this.lssuedDao.findOne(id);
		LssuedDTO lssuedDTO = new LssuedDTO();
		BeanUtils.copyProperties(lssuedEntity, lssuedDTO);
		return lssuedEntity;
	}

	@Override
	public LssuedDetailInfoDTO queryCheckByCheckId(String checkId, Map<String, Object> params) {
		return this.lssuedDao.getLssuedDetailInfo(checkId, params);
	}

	@Override
	public void saveCheckRecord(CheckRecordEntity checkRecordEntity) {
		CheckRecordEntity ct = checkRecordRepository.findOneByCheckId(checkRecordEntity.getCheckId());
		User user = UserUtil.getCurrentUser();
		if(ct != null){
			checkRecordEntity.setId(ct.getId());
			checkRecordEntity.setCreateBy(ct.getCreateBy());
			checkRecordEntity.setCreateName(ct.getCreateName());
			checkRecordEntity.setCreateDate(ct.getCreateDate());
			
		}else{
			checkRecordEntity.setId(UUID.randomUUID().toString());
			checkRecordEntity.setCreateBy(user.getNickname());
			checkRecordEntity.setCreateName(user.getUsername());
			checkRecordEntity.setCreateDate(new Date());
		}
		
		checkRecordRepository.save(checkRecordEntity);
	}

	@Override
	public CheckRecordEntity getRecordBycheckId(String checkId) {
		return this.checkRecordRepository.findOneByCheckId(checkId);
	}

	@Override
	public List<FlowDTO> temsave(LssuedEntity lssuedEntity) {
			String perS =lssuedEntity.getPersonId();
			System.out.println(perS);
			User user = UserUtil.getCurrentUser();
			lssuedEntity.setCreateDate(new Date());
			lssuedEntity.setCreateBy(user.getUsername());
			lssuedEntity.setCreateName(user.getNickname());
			lssuedEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
			lssuedEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
			LssuedEntity lssuedEntitycopy= new LssuedEntity();
			if(lssuedEntity.getId()==null || lssuedEntity.getId().equals("")){
				lssuedEntity.setId(UUID.randomUUID().toString());
				// 案件编号,生成规则(处罚-机构-日期-4位序列  )  e.g. CF-15010001003-20171130-0003
				lssuedEntity.setCheckNum(this.getCheckNum());
				BeanUtils.copyProperties(lssuedEntity,lssuedEntitycopy);
				lssuedEntitycopy.setPersonId(perS);
				lssuedDao.temsave(lssuedEntitycopy);
				saveLssuedPerson(lssuedEntitycopy);
			}else{
				BeanUtils.copyProperties(lssuedEntity,lssuedEntitycopy);
				lssuedEntitycopy.setPersonId(perS);
				lssuedDao.update(lssuedEntitycopy);
				
				lssuedDao.del(lssuedEntitycopy.getId());
				saveLssuedPerson(lssuedEntitycopy);
			}
		return null;
	}
	
	@Override
	public void appSave(LssuedEntity lssuedEntity) {
			String perS =lssuedEntity.getPersonId();
			//当前登录人
			PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
			PersonDTO user =this.personDao.findUserInfo(personDTO.getId());
			//下一节点处理人
			PersonDTO nextUser = this.personDao.findUserInfo(perS);
			
			lssuedEntity.setCreateDate(new Date());
			lssuedEntity.setCreateBy(user.getCertNum());
			lssuedEntity.setCreateName(user.getName());
			lssuedEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
			lssuedEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
			LssuedEntity lssuedEntitycopy= new LssuedEntity();
			if(lssuedEntity.getId()==null || lssuedEntity.getId().equals("")){
				lssuedEntity.setId(UUID.randomUUID().toString());
				// 案件编号,生成规则(处罚-机构-日期-4位序列  )  e.g. CF-15010001003-20171130-0003
				lssuedEntity.setCheckNum(this.getCheckNumForApp());
				BeanUtils.copyProperties(lssuedEntity,lssuedEntitycopy);
				lssuedEntitycopy.setPersonId(perS);
				lssuedDao.temsave(lssuedEntitycopy);
				saveLssuedPerson(lssuedEntitycopy);
			}else{
				BeanUtils.copyProperties(lssuedEntity,lssuedEntitycopy);
				lssuedEntitycopy.setPersonId(perS);
				lssuedDao.update(lssuedEntitycopy);				
				lssuedDao.del(lssuedEntitycopy.getId());
				saveLssuedPerson(lssuedEntitycopy);
			}
			if ( lssuedEntity.getCheckWay().equals(CommonParameters.CheckStatus.LOCATE_CHECK.toString())) {
				FlowDTO flowDTO = new FlowDTO();	
				flowDTO.setAssignee(user.getId());
				flowDTO.setBusinessId(lssuedEntity.getCheckNum());
				flowDTO.setComment(lssuedEntity.getComment());
				flowDTO.setNextAssignee(nextUser.getId());
				flowDTO.setHandleMode(CommonParameters.CheckDailyFlow.TO_XCJC.toString());
				flowDTO.setFlowKey(CommonParameters.FlowKey.PRO_CHECK);
				this.flowService.startFlowByKey(flowDTO);
			}
	}

	@Override
	public List<CheckDocDTO> queryDocContentByCaseId(String checkId) {
		return this.lssuedDao.findCheckDoc(checkId);
	}

	@Override
	public LssuedEntity updateCheck(LssuedEntity lssuedEntity) {
		lssuedDao.updateCheck(lssuedEntity);
		return null;
	}

	@Override
	public List<LssuedDTO> getCaseSourceCheck() {
		User user = UserUtil.getCurrentUser();
		// 查询本部门的 未受理的 经过案件受理节点的 所有日常检查记录
		return this.lssuedDao.getCaseSourceCheck(user.getDept_id(), CommonParameters.Effect.NOT_EFFECT, CommonParameters.Effect.EFFECT);
	}
	
	@Override
	public List<LssuedDTO> getCaseSourceCheck(String deptId) {
		// 查询本部门的 未受理的 经过案件受理节点的 所有日常检查记录
		return this.lssuedDao.getCaseSourceCheck(deptId, CommonParameters.Effect.NOT_EFFECT, CommonParameters.Effect.EFFECT);
	}
	
	@Override
	public void updateCaseAcceptByCheckNum(String businessKey, String expressionText) {
		this.lssuedDao.updateCaseAcceptByCheckNum(businessKey, CommonParameters.Effect.EFFECT);
	}
	
	@Override
	public void updateisRelateByCheckNum(String businessKey, String expressionText) {
		this.lssuedDao.updateisRelateByCheckNum(businessKey, CommonParameters.Effect.EFFECT);
	}

	@Override
	public void updateIsRelateLssued(String casekId) {
		//根据案件ID修改关联好的检查案件字段
		 this.lssuedDao.updateNewIsRelateLssued(casekId);
	}

	@Override
	public LssuedEntity getLssuedByCheckNum(String checkNum) {
		return this.lssuedDao.findByCheckNum(checkNum);
	}

	@Override
	public CheckRecordEntity findRecord(String id) {
		return this.lssuedDao.findRecordById(id);
	}
	
	@Override
	public Integer needDealCount(String id) {
		return this.lssuedDao.needDealCount(id);
	}
	
	@Override
	public Integer caseCountByStatus(int status) {
		return this.lssuedDao.caseCountByStatus(status);
	}
}
