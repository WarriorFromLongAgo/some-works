package com.orhonit.ole.report.service.check;

import com.orhonit.ole.report.dto.check.CheckDTO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CheckService {
	
	//获取各部门专项检查数量
	List<CheckDTO> findOnlyCheck(String areaId);
	
	//获取各个部门的检查总量（专项检查和日常检查）
	List<Object> findDayAndSpecialCount(String areaId);
	
	//获取某个部门的每月检查总量（专项检查和日常检查）
	List<Object> findDayAndMonthly(@Param("params") Map<String, Object> params);
	
	//获取部门合格率
	List<Object> getQualificationCount(@Param("params") Map<String, Object> params);
	
	//获取某个部门的合格率 
	List<Object> getQualificationItemCount(@Param("params") Map<String, Object> params);
}
