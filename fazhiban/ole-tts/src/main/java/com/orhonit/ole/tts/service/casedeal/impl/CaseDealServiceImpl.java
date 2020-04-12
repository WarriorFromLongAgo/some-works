package com.orhonit.ole.tts.service.casedeal.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.CaseDealDao;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.entity.CaseDealEntity;
import com.orhonit.ole.tts.entity.CaseEntity;
import com.orhonit.ole.tts.entity.CheckDailyEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.repository.CaseDealRepository;
import com.orhonit.ole.tts.repository.CaseRepository;
import com.orhonit.ole.tts.repository.CheckDailyRepository;
import com.orhonit.ole.tts.repository.LssuedRepository;
import com.orhonit.ole.tts.service.casedeal.CaseDealService;
@Service
public class CaseDealServiceImpl implements CaseDealService {
	
	@Autowired
	private CaseDealRepository caseDealServiceImpl;
	
	@Autowired
	private CaseDealRepository caseDealRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private CheckDailyRepository checkDailyRepository;
	
	@Autowired
	private LssuedRepository lssuedRepository;
	
	@Autowired
	private CaseDealDao caseDealDao;
	
	@Override
	public List<CaseDealEntity> getCaseDealByCaseId(String caseId) {
		return this.caseDealDao.getCaseDealByCaseId(caseId);
	}

	@Override
	public void saveTaskEntity(FlowDTO flowDTO, Boolean isStart) {
		CaseDealEntity casedeal = new CaseDealEntity();
		casedeal.setIsDeal(CommonParameters.Effect.NOT_EFFECT);
		User user = UserUtil.getCurrentUser();
		if ( flowDTO.getFlowKey().equals(CommonParameters.FlowKey.CASE)) {
			CaseEntity caseEntity = this.caseRepository.findByCaseNum(flowDTO.getBusinessId());
			if ( isStart ) {
				casedeal.setCaseStatus(CommonParameters.CaseStatus.AJSL);
			} else {
				casedeal.setCaseStatus(caseEntity.getCaseStatus());
			}
			casedeal.setCaseId(caseEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(user.getUsername());
			casedeal.setMglCreateName(caseEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setMglDealContent(caseEntity.getMglBriefCaseContent());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(user.getNickname());
			if(flowDTO.getHandleMode() != null && !"".equals(flowDTO.getHandleMode())){
				casedeal.setDealMode(flowDTO.getHandleMode());
			}else if(flowDTO.getSingleMode() != null && !"".equals(flowDTO.getSingleMode())){
				casedeal.setDealMode(flowDTO.getSingleMode());
			}
			casedeal.setDealContent(flowDTO.getComment());
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			this.caseDealRepository.save(casedeal);
		} else if( flowDTO.getFlowKey().equals(CommonParameters.FlowKey.DAY_CHECK)){
			CheckDailyEntity checkDailyEntity = this.checkDailyRepository.findByCheckNum(flowDTO.getBusinessId());
			casedeal.setCaseStatus(Integer.valueOf(checkDailyEntity.getStatus()).intValue());
			casedeal.setCaseId(checkDailyEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(user.getUsername());
			casedeal.setMglCreateName(checkDailyEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(user.getNickname());
			if(flowDTO.getHandleMode() != null && !"".equals(flowDTO.getHandleMode())){
				casedeal.setDealMode(flowDTO.getHandleMode());
			}else if(flowDTO.getSingleMode() != null && !"".equals(flowDTO.getSingleMode())){
				casedeal.setDealMode(flowDTO.getSingleMode());
			}
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			casedeal.setDealContent(flowDTO.getComment());
			this.caseDealRepository.save(casedeal);
			
		}else{
			LssuedEntity lssuedEntity = this.lssuedRepository.findByCheckNum(flowDTO.getBusinessId());
			casedeal.setCaseStatus(Integer.valueOf(lssuedEntity.getStatus()).intValue());
			casedeal.setCaseId(lssuedEntity.getId());
			casedeal.setId(UUID.randomUUID().toString());
			casedeal.setCreateBy(user.getUsername());
			casedeal.setMglCreateName(lssuedEntity.getMglCreateName());
			casedeal.setCreateDate(new Date());
			casedeal.setMglDealContent(lssuedEntity.getComment());
			casedeal.setCaseNum(flowDTO.getBusinessId());
			casedeal.setCreateName(user.getNickname());
			casedeal.setDealMode(flowDTO.getHandleMode());
			casedeal.setDealContent(flowDTO.getComment());
			if(flowDTO.getDealType() != null){
				casedeal.setDealType(Integer.valueOf(flowDTO.getDealType()).intValue());
			}
			this.caseDealRepository.save(casedeal);
		}
	}
}
