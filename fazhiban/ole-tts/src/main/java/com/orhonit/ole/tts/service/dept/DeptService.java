package com.orhonit.ole.tts.service.dept;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.DeptDTO;
import com.orhonit.ole.tts.dto.ps.PsAreaDeptDTO;
import com.orhonit.ole.tts.dto.ps.PsDeptListDTO;


/**
 * 执法主体服务类
 * @author liuzhi
 *
 */
public interface DeptService {
	/**
	 * 根据areaId和name查询主体列表
	 * @param areaId  name
	 * @return
	 */
	List<PsDeptListDTO> getDeptList(Map<String, Object> paramMap);
	
	/**
	 * 查询各区域下主体数量
	 * @param 
	 * @return
	 */
	List<PsAreaDeptDTO> areaDeptList();
	
	/**
	 * 各辖区主体数量和性质统计
	 * @param 
	 * @return
	 */
	List<PsAreaDeptDTO> areaDeptProList(Map<String, Object> paramMap);
	
	/**
	 * 根据areaId和deptId查询主体列表
	 * @param areaId  deptId
	 * @return
	 */
	List<PsAreaDeptDTO> getDeptAllList(Map<String, Object> paramMap);
	/**
	 * 根据主体Id查询本主体及下级主体列表
	 * @param  deptId
	 * @return
	 */
	List<PsDeptListDTO> getDeptListByDeptId(Map<String, Object> paramMap);
	
	/**
	 * 执法主体下执法人员、法律、权责、委托部门、案件信息统计
	 * @param  deptId
	 * @return
	 */
	PsDeptListDTO getCountByDeptId(Map<String, Object> paramMap);
	/**
	 * 根据区域Id和区域类型查询主体
	 * @param  deptId
	 * @return
	 */
	List<PsDeptListDTO> selDeptByDeptId(Map<String, Object> paramMap);
	/**
	 * 根据主体ID查询主体信息
	 * */
	DeptDTO  findDeptInfoById(String deptId);
	
	List<DeptDTO> deptTreeByAreaId(String deptId);

	List<DeptDTO> deptTreeAll();

	List<DeptDTO> deptTreeByDeptId(String dept_id);
}
