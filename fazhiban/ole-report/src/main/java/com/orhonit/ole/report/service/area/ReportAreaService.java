package com.orhonit.ole.report.service.area;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.AreaDTO;
import com.orhonit.ole.report.dto.area.ReportAreaDTO;
import com.orhonit.ole.report.dto.cases.AreaCaseCountDTO;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;

public interface ReportAreaService {

	// 查询所有区域信息
	List<AreaDTO> findArea();

	// 查询区域面积
	List<AreaDTO> findAreaSize();

	// 查询各区域某一年内的案件数量
	List<ReportAreaDTO> findAreaCaseCount(@Param("params") Map<String, Object> params);

	// 查询各区域某一年内地的执法人员数量
	List<ReportAreaDTO> findAreaLowPersonCount(@Param("params") Map<String, Object> params);

	// 查询各区域某一年内的复议案件数
	List<ReportAreaDTO> findReconsiderationCount(@Param("params") Map<String, Object> params);
	
	//查询各区域处理行政案件
	List<AreaCaseCountDTO> findAreaAndCaseCount(@Param("year")String year);
	
	//查询部门人均执法量
	List<DeptPersonAvgDTO> findAVGPersonCaseCount(@Param("year")String year,String areaId);
	
	//查询某地某年个月案件数量
	String[][] findCaseCountByAreaName(@Param("year")String year);
	
	

}
