package com.orhonit.ole.report.dao.cases;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.cases.AreaCaseCountDTO;

@Mapper
public interface ReportAreaAdminCaseDao {
	
	//查询所有的区域列表列表
	List<AreaCaseCountDTO> findAreaList();
	
	//查询各区域处理行政案件
	List<AreaCaseCountDTO> findAreaAndCase(@Param("year")String year);
}
