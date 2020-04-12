package com.orhonit.ole.report.service.nation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.nation.NationDTO;

public interface ReportNationService {
	
	List<NationDTO> getArea();
	
	List<NationDTO> getAreaPost(@Param("params")Map<String, Object> params);
}
