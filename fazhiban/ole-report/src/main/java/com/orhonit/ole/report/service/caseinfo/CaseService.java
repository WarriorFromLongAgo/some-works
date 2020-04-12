package com.orhonit.ole.report.service.caseinfo;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.orhonit.ole.report.dto.CaseInfoDTO;
import com.orhonit.ole.report.entity.CaseEntity;

public interface CaseService {

	/**
	 * 保存案件
	 * @param caseEntity
	 */
	void save(CaseEntity caseEntity);

	/**
	 * 案件受理列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	Page<CaseEntity> getCaseList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * 获取案件内容
	 * @param id
	 * @return
	 */
	CaseInfoDTO findOne(String id);

}
