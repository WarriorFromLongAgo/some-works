package com.orhonit.ole.report.dao.enforce;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;

@Mapper
public interface ReportEnforcePersonDao {

	//查询各区域执法人员总数
	List<BaseChartDTO> findAreaEnforcePersonCount();
	
	//查询执法人员各年龄段分布
	List<BaseChartDTO> findEnforcePersonAge();
	
	//查询各职位执法人员数量
	List<BaseChartDTO> findDutyPersonCount();
	
	//查询人员表中的各种民族数量
	List<BaseChartDTO> findPersonNation();
	
	//学历分布
	List<BaseChartDTO> findEduList();
	
	//政治面貌分布
	List<BaseChartDTO> findpoliticalList();
	
}
