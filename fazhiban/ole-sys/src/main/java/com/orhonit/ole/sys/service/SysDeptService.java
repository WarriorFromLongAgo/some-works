package com.orhonit.ole.sys.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.orhonit.ole.sys.model.DeptDTO;

/**
 * 执法主体服务类
 * @author 武跃忠
 *
 */
public interface SysDeptService {
	
	/**
	 * 根据主体ID查询主体信息
	 * */
	DeptDTO findDeptById(String deptId);
	
	/**
	 * 获取某区划主体树  旗县区法制办
	 * @param deptId
	 * @return
	 */
	List<DeptDTO> deptTreeByAreaId(String areaId);

	/**
	 * 获取全部主体的主体树   市法制办/管理员
	 * @return
	 */
	List<DeptDTO> deptTreeAll();

	/**
	 * 获取某主体及其下级主体的主体树	委办局
	 * @param dept_id
	 * @return
	 */
	List<DeptDTO> deptTreeByDeptId(String deptId);
	
	/**
	 * 获取某主体及其下级主体的主体树	委办局
	 * @param dept_id
	 * @return
	 */
	List<DeptDTO> deptTreeByParentId(String parentId);
	
	List<DeptDTO> deptListByParentId(String parentId);
	
	String deptListByParentIdString(@RequestParam("parentId")String parentId);
	
	List<DeptDTO> deptTreeByNameLike(String name);
	
	/**
	 * 根据当前登录账号的主体属性获取主体列表
	 * @return
	 */
	String getDepts();
	
	String getDeptsApp(String deptId);
}
