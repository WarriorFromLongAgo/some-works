package com.orhonit.ole.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.report.dto.DeptDTO;
import com.orhonit.ole.report.dto.ps.AreaDeptDTO;
import com.orhonit.ole.report.dto.ps.DeptListDTO;

@Mapper
public interface ReportDeptDao {
	
	/**
	 * 获取执法主体
	 * @param params
	 * 
	 * 
	 * @return
	 */
	@Select("select * from ole_base_dept t where t.id = #{id}")
	DeptDTO getDeptById(@Param("id") String id);
	
	/**
	 * 获取执法主体列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<DeptListDTO> getDeptList(@Param("params") Map<String, Object> params);
	
	
	/**
	 * 各辖区主体数量统计
	 * @param 
	 * 
	 * 
	 * @return
	 */
	List<AreaDeptDTO> areaDeptList();
	
	/**
	 * 各辖区主体数量和性质统计
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<AreaDeptDTO> areaDeptProList(@Param("params") Map<String, Object> params);
	/**
	 * 获取执法主体详细列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<AreaDeptDTO> getDeptAllList(@Param("params") Map<String, Object> params);
	/**
	 * 根据主体Id查询本主体及下级主体列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<DeptListDTO> getDeptListByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * 执法人员总数
	 * @param params
	 * 
	 * 
	 * @return
	 */
	String getPersonCountByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * 法律总数
	 * @param params
	 * 
	 * 
	 * @return
	 */
	String getLawCountByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * 委托机构总数
	 * @param params
	 * 
	 * 
	 * @return
	 */
	String getDeptAgentCountByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * 权责数量
	 * @param params
	 * 
	 * 
	 * @return
	 */
	String getPotenceCountByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * 案件数量
	 * @param params
	 * 
	 * 
	 * @return
	 */
	String getcaseCountByDeptId(@Param("params") Map<String, Object> params);
	
	/**
	 * 获取当前部门及下级部门
	 * @param deptId
	 * @return
	 */
	String getDeptAndChildById(@Param("deptId") String deptId);

	/**
	 * 获取有案件的主体
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getDeptHaveCase(@Param("params")Map<String, Object> params);
}
