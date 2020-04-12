package com.orhonit.ole.report.dao.check;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.check.CheckQualificationDTO;

@Mapper
public interface CheckQualificationDao {
	
	//获取日常检查合格总数
	List<CheckQualificationDTO> getDayQualificationCount(@Param("params") Map<String,Object> params);
	
	//获取专项检查合格总数
	List<CheckQualificationDTO> getSpecialQualificationCount(@Param("params") Map<String,Object> params);
	
	//根据部门获取部门的所有检查总数（日常）
	List<CheckQualificationDTO> getDayCount(@Param("params") Map<String,Object>	params);
	
	//根据部门获取部门的所有检查总数（专项）
	List<CheckQualificationDTO> getSpecialCount(@Param("params") Map<String,Object>	params);
	
}
