package com.orhonit.ole.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.orhonit.ole.sys.model.InterfaceLogs;


@Mapper
public interface InterfaceLogsDao {

	int InterfaceLogcount(@Param("params") Map<String, Object> params);

	List<InterfaceLogs> InterfaceLoglist(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);
	
	InterfaceLogs InterfaceLogInfo(@Param("logId") String logId);
	
	
}
