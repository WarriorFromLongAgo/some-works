package com.orhonit.ole.report.dao.cases;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.cases.CaseSimpDTO;

@Mapper
public interface ReportCSimpDao {
	
	List<CaseSimpDTO> getCaseCount1();
		
	List<CaseSimpDTO> getCaseCount(@Param("params") Map<String,Object> params);
}
