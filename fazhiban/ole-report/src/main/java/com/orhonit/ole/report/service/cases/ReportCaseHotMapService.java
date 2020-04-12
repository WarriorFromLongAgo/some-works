package com.orhonit.ole.report.service.cases;

import java.util.List;

import com.orhonit.ole.report.dto.cases.CaseHotMapDTO;

public interface ReportCaseHotMapService {
	
	//查询案件库中年份字典排序
	List<String> findCaseYearSort();

	//查询每日案件信息；用于案件发生热点图
	List<CaseHotMapDTO> findDailyCase();
}
