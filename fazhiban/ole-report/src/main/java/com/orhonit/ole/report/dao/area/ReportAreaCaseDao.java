package com.orhonit.ole.report.dao.area;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.area.ReportAreaDTO;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;


/**
 * 
 * @author Jwen
 * 区域地图Dao
 */
@Mapper
public interface ReportAreaCaseDao {

	//查询各区域某一年内的案件数量
	List<ReportAreaDTO> findAreaCaseCount(@Param("params") Map<String, Object> params);
	
	//查询各区域某一年内地的执法人员数量
	List<ReportAreaDTO> findAreaLowPersonCount(@Param("params") Map<String, Object> params);
	
	//查询各区域某一年内的复议案件数
	List<ReportAreaDTO> findReconsiderationCount(@Param("params") Map<String, Object> params);
	
	//查询某地某年个月案件数量
	List<BaseChartDTO> findCaseCountByAreaName(@Param("areaName")String areaName,@Param("year")String year);
}
