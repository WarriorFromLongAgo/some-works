package com.orhonit.ole.report.service.cases;

import com.orhonit.ole.report.dto.cases.CaseWranDTO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 案件异常统计
 * @author Jwen
 *
 */
public interface ReportCaseWranService {

	//统计某种状态案件的数量
	CaseWranDTO findCaseCountByCaseStatus(@Param("num")String num);
	
	//统计各类异常总数
	List<CaseWranDTO> findCaseWranCount(String depts);

	//统计案件总数
	CaseWranDTO findCaseCount();

	List<Map<String, Object>> getDeptCaseWarn(Map<String, Object> params);
}
