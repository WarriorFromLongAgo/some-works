package com.orhonit.ole.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysAreaDao {
	
	String areaIdByAreaId(@Param("areaId") String areaId);
}
