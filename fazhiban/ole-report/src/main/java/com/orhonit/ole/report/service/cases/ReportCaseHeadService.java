package com.orhonit.ole.report.service.cases;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.cases.CaseHeadDTO;

/**
 * 案件处理结果统计
 * @author Jwen
 *
 */
public interface ReportCaseHeadService {
	
	//查询每年某个节点的数量
	List<CaseHeadDTO> findCaseByCaseStatus(@Param ("status")String status);
	
	//查询每年某个节点的数量
	List<CaseHeadDTO> findCaseByCaseStatus1(@Param ("status")String status);
	
	//查詢某年每個月份案件节点的数量
	List<CaseHeadDTO> findCaseByCaseStatusAndYear(@Param ("status")String status,@Param ("year")String year);
	
	//查詢某年每個月份案件节点的数量
	List<CaseHeadDTO> findCaseByCaseStatusAndYear1(@Param ("status")String status,@Param ("year")String year);
}
