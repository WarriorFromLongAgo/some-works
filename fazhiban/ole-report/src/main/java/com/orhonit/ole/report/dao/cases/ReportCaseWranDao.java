package com.orhonit.ole.report.dao.cases;

import com.orhonit.ole.report.dto.cases.CaseWranDTO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportCaseWranDao {

	//统计某种状态案件的数量
	CaseWranDTO findCaseCountByCaseStatus(@Param("num")String num);

	//统计案件总数
	CaseWranDTO findCaseCount();
	
	//统计各类异常总数
	List<CaseWranDTO> findCaseWranCount(@Param("deptIds")String deptIds);

	//按年获取本部门及下级部门的预警总数
	List<Map<String, Object>> getDeptCaseWarn(@Param("params")Map<String, Object> params);
}
