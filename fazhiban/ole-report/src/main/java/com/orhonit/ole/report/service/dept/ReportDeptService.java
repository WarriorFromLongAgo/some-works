package com.orhonit.ole.report.service.dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.DeptDTO;
import com.orhonit.ole.report.dto.cases.DeptCaseCountDTO;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonAvgDTO;
import com.orhonit.ole.report.dto.dept.DeptPersonDistDTO;
import com.orhonit.ole.report.dto.ps.AreaDeptDTO;

public interface ReportDeptService {
	
	List<AreaDeptDTO> areaDeptList();
	
	List<AreaDeptDTO> areaDeptProList(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询行政部门行政案件数量统计
	 */
	List<DeptCaseCountDTO> DeptCaseCount(String year,String areaId);
	
	/**
	 * 查询部门列表
	 * @param areaId
	 * @return
	 */
	List<DeptPersonAvgDTO> findDeptList(String areaId);

	/**
	 * 查询部门人数
	 * @param areaId
	 * @return
	 */
	List<DeptPersonAvgDTO> findDeptPersonCount(String areaId);
	
	/**
	 * 查询部门行政执法案件量
	 * @param year
	 * @param areaId
	 * @return
	 */
	List<DeptPersonAvgDTO> findDeptCaseCount(String year,String areaId);
	
	/**
	 * 查询部门人员分布（执法或监督数量）
	 * @param lawType
	 * @return
	 */
	List<DeptPersonDistDTO> findDeptPersonByLawType(String lawType);
	
	/**
	 * 查询执法和监督人员数量
	 * @return
	 */
	List<BaseChartDTO> findPersonLawType();
	
	/**
	 * 根据ID获取部门
	 * @param id
	 * @return
	 */
	DeptDTO findDeptById(String id);
	
	/**
	 * 获取当前及其下级主体列表
	 * @param id
	 * @return
	 */
	String getDeptAndChildById(String id);

	/**
	 * 获取有案件的主体
	 * @param params
	 * @return 
	 */
	List<Map<String, Object>> getDeptHaveCase(Map<String, Object> params);

}
