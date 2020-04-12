package com.orhonit.ole.report.service.cases;

import com.orhonit.ole.report.dto.cases.DeptWranDTO;

import java.util.List;
import java.util.Map;

/**
 * 案件处理结果统计
 * @author Jwen
 *
 */
public interface ReportDeptWranInfoService {

	//各部门红黄蓝总和
	List<DeptWranDTO> findDeptWranCount(String areaId);

	//全市各部门预警总和
	List<DeptWranDTO> findAreaWranCount();

	/**
	 * 获取全部基础信息预警
	 * @return
	 */
	List<Map<String,Object>> findBaseWranCount();
	
	/**
	 * 获取基础信息预警详细数据
	 * @param taskList
	 * @return
	 */
	List<Map<String, Object>> findBaseWranDetail(String taskList);
}
