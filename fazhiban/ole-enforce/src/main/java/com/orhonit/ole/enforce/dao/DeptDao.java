package com.orhonit.ole.enforce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.enforce.dto.DeptDTO;
import com.orhonit.ole.enforce.dto.ps.PsAreaDeptDTO;
import com.orhonit.ole.enforce.dto.ps.PsDeptListDTO;

@Mapper
public interface DeptDao {
	
	/**
	 * 获取执法主体列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<PsDeptListDTO> getDeptList(@Param("params") Map<String, Object> params);
	
	
	/**
	 * 各辖区主体数量统计
	 * @param 
	 * 
	 * 
	 * @return
	 */
	List<PsAreaDeptDTO> areaDeptList();
	
	/**
	 * 各辖区主体数量和性质统计
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<PsAreaDeptDTO> areaDeptProList(@Param("params") Map<String, Object> params);
	/**
	 * 获取执法主体详细列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<PsAreaDeptDTO> getDeptAllList(@Param("params") Map<String, Object> params);
	/**
	 * 根据主体Id查询本主体及下级主体列表
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<PsDeptListDTO> getDeptListByDeptId(@Param("params") Map<String, Object> params);
	
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
	 * 根据区域Id和区域类型查询主体
	 * @param params
	 * 
	 * 
	 * @return
	 */
	List<PsDeptListDTO>  selDeptByDeptId(@Param("params") Map<String, Object> params);
	/**
	 * 根据主体ID查询主体信息
	 * */
	DeptDTO findDeptInfoById(@Param("deptId")String deptId);
	
	@Select("SELECT * FROM ole_base_dept WHERE area_id = #{id} and if_effect='1' and del_flag ='0'")
	List<DeptDTO> deptTreeByAreaId(String id);
	
	@Select("SELECT * FROM ole_base_dept where if_effect='1' and del_flag ='0'")
	List<DeptDTO> deptTreeAll();

	@Select("SELECT * FROM ole_base_dept WHERE  if_effect='1' and del_flag ='0' and FIND_IN_SET(id,getBaseDept(#{id}))")
	List<DeptDTO> deptTreeByDeptId(String id);
}
