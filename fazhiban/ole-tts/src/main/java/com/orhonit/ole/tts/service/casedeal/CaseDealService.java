package com.orhonit.ole.tts.service.casedeal;

import java.util.List;

import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.entity.CaseDealEntity;

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
}
