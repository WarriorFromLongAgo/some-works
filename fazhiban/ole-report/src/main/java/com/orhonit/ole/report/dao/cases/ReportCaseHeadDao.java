package com.orhonit.ole.report.dao.cases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.cases.CaseHeadDTO;

@Mapper
public interface ReportCaseHeadDao {
	
	//查询每年的案件数量
	List<CaseHeadDTO> findYearCaseCount();
	
	//查询每年某个节点的数量
	List<CaseHeadDTO> findCaseByCaseStatus(@Param ("status")String status);
	
	//查询每年某个节点的数量
	List<CaseHeadDTO> findCaseByCaseStatus1(@Param ("status")String status);
	
	//查詢某年每個月份案件节点的数量
	List<CaseHeadDTO> findCaseByCaseStatusAndYear(@Param ("status")String status,@Param ("year")String year);
	
	//查詢某年每個月份案件节点的数量
	List<CaseHeadDTO> findCaseByCaseStatusAndYear1(@Param ("status")String status,@Param ("year")String year);
}
