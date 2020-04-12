package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.entity.LssuedEntity;

@Mapper
public interface CheckQueryDao  {
	String[] DELETE = null;

	Integer count(@Param("params") Map<String, Object> params);

	List<LssuedEntity> list(@Param("params") Map<String, Object> params, @Param("start") Integer start,
			@Param("length") Integer length);

	String getCheckZfryIdByCheckNum(@Param("checkNum")String checkNum);

	Map<String, Object> getCheck(@Param("checkNum")String checkNum);
}
