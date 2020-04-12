package com.orhonit.ole.report.service.person;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.PersonDTO;

public interface ReportPersonService {
	
	//查询所有区域中各执法主体下的专项检查数量
	public List<PersonDTO> getAreaDeptCheck();
	
	//查询所有区域中各执法主体下的日常检查数量
	public List<PersonDTO> getAreaDeptCheckDaily();
	
	//查询指定区域主体下的日常执法次数
	public List<PersonDTO> getAreaDeptCheckByAreaName(@Param("params")Map<String, Object> params);

}
