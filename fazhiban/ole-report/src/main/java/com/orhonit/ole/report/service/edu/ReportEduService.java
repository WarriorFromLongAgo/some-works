package com.orhonit.ole.report.service.edu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.edu.EduDTO;


public interface ReportEduService {
	
	List<EduDTO> getArea();
	
	List<EduDTO> getAreaPost(@Param("params")Map<String, Object> params);
}
