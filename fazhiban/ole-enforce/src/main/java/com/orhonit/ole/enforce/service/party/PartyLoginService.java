package com.orhonit.ole.enforce.service.party;

import com.orhonit.ole.enforce.entity.PartyLoginEntity;

public interface PartyLoginService {

	/**
	 * 保存
	 * @param PartyLoginEntity
	 */
	void savePartyLogin(PartyLoginEntity partyLoginEntity);
	
	/**
	 * 查询
	 * @param loginName
	 * @return
	 */
	PartyLoginEntity findPartyLoginByLoginName(String loginName);

	/**
	 * 根据caseId创建当事人用于登录公示系统的账号
	 * @param caseId
	 * @param hearingDate 
	 * @return
	 */
	Boolean createPartyLogin(String caseId, String hearingDate);
}
