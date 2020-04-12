package com.orhonit.ole.report.service.inspect;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.position.PositionDTO;


public interface ReportPositionService {
	
	List<PositionDTO> getArea();
	
	List<PositionDTO> getAreaPost(@Param("params")Map<String, Object> params);
}
