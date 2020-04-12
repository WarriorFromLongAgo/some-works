package com.orhonit.ole.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.age.AgeDTO;
import com.orhonit.ole.report.dto.edu.EduDTO;
import com.orhonit.ole.report.dto.nation.NationDTO;
import com.orhonit.ole.report.dto.political.PoliticalDTO;
import com.orhonit.ole.report.dto.position.PositionDTO;

@Mapper
public interface ReportAgeDao {
	//age年龄分布
	List<AgeDTO> getBirthday();

	List<AgeDTO> getArea();
	
	//edu学历分布
	List<EduDTO> getArea1();
	
	List<EduDTO> getAreaPost(@Param("params") Map<String, Object> params);
	
	//political政治面貌分布
	List<PoliticalDTO> getArea2();
	
	List<PoliticalDTO> getAreaPost2(@Param("params") Map<String, Object> params);
	
	//position职位分布
	List<PositionDTO> getArea3();
	
	List<PositionDTO> getAreaPost3(@Param("params") Map<String, Object> params);
	
	//nation民族分布
	List<NationDTO> getArea4();
		
	List<NationDTO> getAreaPost4(@Param("params") Map<String, Object> params);



}