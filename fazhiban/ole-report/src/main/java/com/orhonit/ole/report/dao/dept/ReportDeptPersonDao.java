package com.orhonit.ole.report.dao.dept;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonDistDTO;

@Mapper
public interface ReportDeptPersonDao {

	//查询部门列表
	List<DeptPersonAvgDTO> findDeptList(@Param("areaId")String areaId);

	//查询部门人数
	List<DeptPersonAvgDTO> findDeptPersonCount(@Param("areaId")String areaId);
	
	//查询部门行政执法案件量
	List<DeptPersonAvgDTO> findDeptCaseCount(@Param("year")String year,@Param("areaId")String areaId);
	
	//查询部门人员分布（执法或监督数量）
	List<DeptPersonDistDTO> findDeptPersonByLawType(@Param("lawType")String lawType);
	
	//查询执法和监督人员数量
	List<BaseChartDTO> findPersonLawType();
}
