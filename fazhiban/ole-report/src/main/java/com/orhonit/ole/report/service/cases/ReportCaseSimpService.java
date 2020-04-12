package com.orhonit.ole.report.service.cases;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.cases.CaseSimpDTO;

public interface ReportCaseSimpService {
	
	List<CaseSimpDTO> getCaseCount1();
	
	//查询案件简易不同的数量
	List<CaseSimpDTO> getCaseCount(@Param("params") Map<String,Object> params);
}
