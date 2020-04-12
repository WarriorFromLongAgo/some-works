package com.orhonit.ole.sys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.orhonit.ole.sys.model.DeptDTO;

@Mapper
public interface SysDeptDao {
	
	/**
	 * 根据主体ID查询主体信息
	 * */
	DeptDTO findDeptById(@Param("deptId")String deptId);
	
	/**
	 * 根据区划id查询主体树
	 * @param id
	 * @return
	 */
	List<DeptDTO> deptTreeByAreaId(@Param("id")String id);
	
	/**
	 * 获取所有主体的主体树
	 * @return
	 */
	List<DeptDTO> deptTreeAll();

	/**
	 * 获取主体及下级主体的主体树
	 * @param id
	 * @return
	 */
	List<DeptDTO> deptTreeByDeptId(@Param("id")String id);
	
	/**
	 * 获取主体及下级主体的主体列表
	 * @param deptId
	 * @return
	 */
	String deptListByDeptId(@Param("deptId") String deptId);
	
	String deptIdByAreaId(@Param("areaId") String areaId);
	
	List<DeptDTO> deptTreeByParentId(@Param("parentId")String parentId);
	
	List<DeptDTO> deptListByParentId(@Param("parentId")String parentId);
	
	List<DeptDTO> deptTreeByNameLikes(@Param("name")String name);
	
	/**
	 * 获取某区划下所有主体列表
	 * @param areaId
	 * @return
	 */
	String deptListByAreaId(@Param("areaId") String areaId);
	
	String deptListByParentIdString(@Param("parentId") String parentId);
}
