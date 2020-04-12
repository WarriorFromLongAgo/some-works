package com.orhonit.ole.report.dao.cases;

import com.orhonit.ole.report.dto.cases.DeptWranDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportWranInfoDao {

	//各部门红黄蓝总和
	List<DeptWranDTO> findDeptWranCount(@Param("deptIds")String deptIds);

	//全市各部门预警总和
	List<DeptWranDTO> findAreaWranCount();
	
	//各类型基础信息预警总和
	List<Map<String,Object>> findBaseWranCount();

	//基础信息预警详细信息
	List<Map<String, Object>> findBaseWranDetail(@Param("taskList")String taskList);
}
