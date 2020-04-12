package com.orhonit.ole.enforce.service.party;

import java.util.List;

import com.orhonit.ole.enforce.entity.PartyInfoEntity;

public interface PartyInfoService {

	/**
	 * 根据ID获取当事人信息
	 * @param id
	 * @return
	 */
	PartyInfoEntity findOne(String id);
	
	/**
	 * 根据案件获取当事人
	 * @param caseId
	 * @return
	 */
	PartyInfoEntity findByCaseId(String caseId);
	
	/**
	 * 获取所有当事人
	 * @return
	 */
	List<PartyInfoEntity> findAll();
	
	/**
	 * 保存
	 * @param partyInfoEntity
	 */
	PartyInfoEntity savePartyInfo(PartyInfoEntity partyInfoEntity);
}
