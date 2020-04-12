package com.orhonit.ole.report.dao.cases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.report.dto.cases.CaseHotMapDTO;


/**
 * 
 * @author Jwen
 * 案件发生热点图Dao
 */
@Mapper
public interface ReportCaseHotMapDao {
	
	//查询案件库的年份
	@Select("select DISTINCT(YEAR(case_time)) FROM ole_ef_case")
	List<String> findCaseyear();
	
	//查询日常案件
	List<CaseHotMapDTO> findDailyCase();
}
