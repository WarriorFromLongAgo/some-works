package com.orhonit.ole.enforce.service.casedeal;

import java.util.List;

import com.orhonit.ole.enforce.entity.CaseDealEntity;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.model.User;

public interface CaseDealService {

	/**
	 * 根据案件ID获取所有该案件的所有记录
	 * @param caseId
	 * @return
	 */
	List<CaseDealEntity> getCaseDealByCaseId(String caseId);

	/**
	 * 保存每个人物的相关信息
	 * @param flowDTO
	 * @param isStart
	 */
	void saveTaskEntity(FlowDTO flowDTO, Boolean isStart);
	
	/**
	 * App保存每个人物的相关信息
	 * @param flowDTO
	 * @param isStart
	 */
	void appSaveTaskEntity(FlowDTO flowDTO, Boolean isStart, User user);
}
