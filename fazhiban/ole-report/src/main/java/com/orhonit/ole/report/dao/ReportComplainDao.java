package com.orhonit.ole.report.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportComplainDao {

	Integer getComplainCountByYear(@Param("year")int year);

	Integer getComplainCountByYearMonth(@Param("year")int year, @Param("month")int month);
	
}
