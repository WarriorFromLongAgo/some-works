package com.orhonit.ole.report.service.political;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.political.PoliticalDTO;
import com.orhonit.ole.report.dto.position.PositionDTO;

public interface ReportPoliticalService {
	
	List<PoliticalDTO> getArea();
	
	List<PoliticalDTO> getAreaPost(@Param("params")Map<String, Object> params);
}
