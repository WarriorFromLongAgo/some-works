package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.dto.CaseWranDTO;

@Mapper
public interface DashboardDao {

	Integer getCountByWarnType(@Param("params") Map<String, Object> params);
	
	String getDeptsByParentDeptId(@Param("deptId") String deptId);

	List<CaseWranDTO> findCaseWranCount(@Param("deptIds") String deptIds);

	int haveWarnRead(String createBy);

}
