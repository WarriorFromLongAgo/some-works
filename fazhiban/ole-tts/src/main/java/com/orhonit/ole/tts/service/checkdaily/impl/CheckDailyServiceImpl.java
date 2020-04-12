package com.orhonit.ole.tts.service.checkdaily.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.sys.model.SequenceEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.SequenceRepository;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.CheckDailyDao;
import com.orhonit.ole.tts.dto.CheckDailyDTO;
import com.orhonit.ole.tts.dto.CheckDocDTO;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.api.ApiCaseListDTO;
import com.orhonit.ole.tts.dto.api.ApiDailyCheckDTO;
import com.orhonit.ole.tts.entity.CheckDailyEntity;
import com.orhonit.ole.tts.entity.CheckRecordEntity;
import com.orhonit.ole.tts.repository.CheckDailyRepository;
import com.orhonit.ole.tts.repository.CheckRecordRepository;
import com.orhonit.ole.tts.repository.DeptRepository;
import com.orhonit.ole.tts.service.checkdaily.CheckDailyService;
import com.orhonit.ole.tts.utils.EnforceException;
@Service
public class CheckDailyServiceImpl implements CheckDailyService {
	
private static final String CHECK_NUM_TITLE = "RC";
	
	private static final String CHECK_NUM_PREFIX = "-";
	
	private static final String CHECK_NUM_SEQ_NAME = "case_id_seq";
	
	private static final int CHECK_NUM_PAD_NUM = 4;
	@Autowired
	private CheckDailyRepository checkDailyRepository;
	
	@Autowired
	private CheckRecordRepository checkRecordRepository;
	
	@Autowired
	private CheckDailyDao checkDailyDao;
	
	@Autowired
	private SequenceRepository sequenceRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Override
	@Transactional
	public FlowDTO save(CheckDailyEntity checkEntity) {
		//日常检查新增
		User user = UserUtil.getCurrentUser();
		if(checkEntity.getId()==null || checkEntity.getId().equals("")){
			
			checkEntity.setCreateBy(user.getUsername());
			checkEntity.setCreateDate(new Date());
			checkEntity.setCreateName(user.getNickname());
			checkEntity.setId(UUID.randomUUID().toString());
			
			if(checkEntity.getCheckId()==null || checkEntity.getCheckId().equals("")){
				checkEntity.setCheckId(UUID.randomUUID().toString());
			}
			if(checkEntity.getCheckNum()==null || checkEntity.getCheckNum().equals("")){
				checkEntity.setCheckNum(this.getCheckNum(user));
			}
			checkEntity.setDeptId(user.getDept_id());
			checkEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
			checkEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
			this.checkDailyRepository.save(checkEntity);
			FlowDTO flowDTO = new FlowDTO();
			flowDTO.setAssignee(user.getId().toString());
			flowDTO.setBusinessId(checkEntity.getCheckNum());
			flowDTO.setComment(checkEntity.getComment());
			flowDTO.setNextAssignee(user.getId().toString());
			flowDTO.setFlowKey(CommonParameters.FlowKey.DAY_CHECK);
			return flowDTO;
			}
		else{
			//日常检查编辑
			CheckDailyEntity check = this.checkDailyRepository.findOne(checkEntity.getId());
			check.setCheckedDate(checkEntity.getCheckedDate());
			check.setCheckedUserId(checkEntity.getCheckedUserId());
			check.setCheckedUserName(checkEntity.getCheckedUserName());
			check.setCheckMode(checkEntity.getCheckMode());
			check.setCheckSituation(checkEntity.getCheckSituation());
			check.setCheckTitle(checkEntity.getCheckTitle());
			check.setUnitName(checkEntity.getUnitName());
			check.setRegistNum(checkEntity.getRegistNum());
			check.setLegalPerson(checkEntity.getLegalPerson());
			check.setRoadName(checkEntity.getRoadName());
			check.setPersonId(checkEntity.getPersonId());
			check.setAssistPersonId(checkEntity.getAssistPersonId());
			check.setCheckObjectType(checkEntity.getCheckObjectType());
			checkEntity.setDeptId(user.getDept_id());
			checkEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
			checkEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
			this.checkDailyRepository.save(check);
			return null;
		}
	}
	
	
	@Override
	public Integer getCheckcount(Map<String, Object> params) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.checkDailyDao.getCheckcount(params);
	}
	@Override
	public List<CheckDailyDTO> getCheckList(Map<String, Object> params, Integer start, Integer length) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.checkDailyDao.checkList(params, start, length);
	}


	@Override
	public List<ApiCaseListDTO> findCheckDailyList(Map<String, Object> params) {
		return this.checkDailyDao.findCheckDailyList(params);
	}


	@Override
	public ApiDailyCheckDTO findCaseInfo(Map<String, Object> params) {
		return this.checkDailyDao.findCaseInfo(params);
	}
	
	private String getCheckNum(User user) {
		
		SequenceEntity sequenceEntity = this.sequenceRepository.findOne(CHECK_NUM_SEQ_NAME);
		if ( sequenceEntity == null || sequenceEntity.getSeqDate() == null) {
			throw new EnforceException(ResultCode.BUSI_ERROR.getCode(), CHECK_NUM_SEQ_NAME + ", seq_name is not exits or seq_date is null.");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar now = Calendar.getInstance();
		
		StringBuilder builder = new StringBuilder();
		
		String deptCode = this.deptRepository.findOne(user.getDept_id()).getCode();
		
		builder.append(CHECK_NUM_TITLE)
			.append(CHECK_NUM_PREFIX)
			.append(deptCode)
			.append(CHECK_NUM_PREFIX)
			.append(sdf.format(now.getTime()))
			.append(CHECK_NUM_PREFIX);
		
		Calendar seqDate = Calendar.getInstance();
		
		seqDate.setTime(sequenceEntity.getSeqDate());
		
		if ( now.get(Calendar.YEAR) == seqDate.get(Calendar.YEAR) 
				&& now.get(Calendar.MONTH) == seqDate.get(Calendar.MONTH)
				&& now.get(Calendar.DAY_OF_MONTH) == seqDate.get(Calendar.DAY_OF_MONTH)) {
			
			Integer nextVal = Integer.valueOf(sequenceEntity.getSeqValue()) + 1;
			sequenceEntity.setSeqValue(String.valueOf(nextVal));
			builder.append(
					String.format("%" + CHECK_NUM_PAD_NUM +"s", sequenceEntity.getSeqValue()).replace(' ', '0'));
			
		} else {
			builder.append(
					String.format("%" + CHECK_NUM_PAD_NUM +"s", 1).replace(' ', '0'));
			// 更新序列
			sequenceEntity.setSeqValue("1");
			sequenceEntity.setSeqDate(now.getTime());
		}
		this.sequenceRepository.save(sequenceEntity);
		return builder.toString();
	}


	@Override
	public void updateCheckStatusByCheckNum(String businessKey, String status) {
		this.checkDailyDao.updateCheckStatusByCheckNum(businessKey, status);
		
	}


	@Override
	public CheckDailyDTO queryCheckByCheckId(String checkId, Map<String, Object> params) {
		return this.checkDailyDao.getCheckByCheckId(checkId, params);
	}
	
	@Override
	public CheckDailyDTO queryCheckById(String checkId, Map<String, Object> params) {
		return this.checkDailyDao.getCheckDetailInfo(checkId, params);
	}


	@Override
	public List<CheckDocDTO> queryDocContentByCheckId(String checkId) {
		return this.checkDailyDao.findCheckDoc(checkId);
	}


	@Override
	public CheckDailyDTO findOneCheckDaily(String id) {
		return this.checkDailyDao.findOne(id);
		
	}
	
	@Override
	public CheckRecordEntity findOneCheck(String id) {
		return this.checkRecordRepository.findOneByCheckId(id);
	}

	@Override
	public CheckDailyEntity findByCheckNum(String checkId) {
		return this.checkDailyRepository.findByCheckNum(checkId);
	}


	@Override
	public CheckDailyEntity findByCheckId(String checkId) {
		return this.checkDailyRepository.findOne(checkId);
	}


	@Override
	public List<CheckDailyDTO> getCaseSourceCheck() {
		User user = UserUtil.getCurrentUser();
		// 查询本部门的 未受理的 经过案件受理节点的 所有日常检查记录
		return this.checkDailyDao.getCaseSourceCheck(user.getDept_id(), CommonParameters.Effect.NOT_EFFECT, CommonParameters.Effect.EFFECT);
	}
	
	@Override
	public List<CheckDailyDTO> getCaseSourceCheck(String deptId) {
		// 查询本部门的 未受理的 经过案件受理节点的 所有日常检查记录
		return this.checkDailyDao.getCaseSourceCheck(deptId, CommonParameters.Effect.NOT_EFFECT, CommonParameters.Effect.EFFECT);
	}

	@Override
	public void updateCaseAcceptByCheckNum(String businessKey, String expressionText) {
		this.checkDailyDao.updateCaseAcceptByCheckNum(businessKey, CommonParameters.Effect.EFFECT);
	}


	@Override
	public void saveAppCheckInfo(CheckDailyEntity checkEntity, User user) {
		//日常检查新增
		if(checkEntity.getCheckNum()==null || "".equals(checkEntity.getCheckNum())){
			checkEntity.setStatus(CommonParameters.CheckStatus.CHECK_ZC.toString()); 
			checkEntity.setPersonId(user.getPerson_id());
			checkEntity.setCreateBy(user.getUsername());
			checkEntity.setCreateDate(new Date());
			checkEntity.setCreateName(user.getNickname());
			checkEntity.setId(UUID.randomUUID().toString());
			
			if(checkEntity.getCheckId()==null || checkEntity.getCheckId().equals("")){
				checkEntity.setCheckId(UUID.randomUUID().toString());
			}
			if(checkEntity.getCheckNum()==null || checkEntity.getCheckNum().equals("")){
				checkEntity.setCheckNum(this.getCheckNum(user));
			}
			checkEntity.setDeptId(user.getDept_id());
			checkEntity.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
			checkEntity.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
			this.checkDailyRepository.save(checkEntity);
			}
		else{
			//日常检查编辑
			CheckDailyEntity check = this.checkDailyRepository.findByCheckNum(checkEntity.getCheckNum());
			check.setCheckedDate(checkEntity.getCheckedDate());
			check.setCheckedUserId(checkEntity.getCheckedUserId());
			check.setCheckedUserName(checkEntity.getCheckedUserName());
			check.setCheckMode(checkEntity.getCheckMode());
			check.setCheckSituation(checkEntity.getCheckSituation());
			check.setCheckTitle(checkEntity.getCheckTitle());
			check.setUnitName(checkEntity.getUnitName());
			check.setRegistNum(checkEntity.getRegistNum());
			check.setLegalPerson(checkEntity.getLegalPerson());
			check.setRoadName(checkEntity.getRoadName());
			check.setPersonId(checkEntity.getPersonId());
			check.setAssistPersonId(checkEntity.getAssistPersonId());
			check.setCheckObjectType(checkEntity.getCheckObjectType());
			check.setDeptId(user.getDept_id());
			check.setCaseAccept(CommonParameters.Effect.NOT_EFFECT.toString());
			check.setIsRelate(CommonParameters.Effect.NOT_EFFECT.toString());
			check.setPersonId(user.getPerson_id());
			check.setStatus(CommonParameters.CheckStatus.CHECK_ZC.toString()); 
			this.checkDailyRepository.save(check);
		}
		
	}
	
	@Override
	public void updateIsRelateCheckDaily(String casekId) {
		//根据案件ID修改关联好的检查案件字段
		 this.checkDailyDao.updateIsRelateCheckDaily(casekId);
	}
	
	@Override
	public Integer needDealCount(String id) {
		return this.checkDailyDao.needDealCount(id);
	}
	
	@Override
	public Integer caseCountByStatus(int status) {
		return this.checkDailyDao.caseCountByStatus(status);
	}

}

