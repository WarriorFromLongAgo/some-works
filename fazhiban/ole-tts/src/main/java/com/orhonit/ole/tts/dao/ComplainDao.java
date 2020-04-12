package com.orhonit.ole.tts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.dto.ComplainDTO;

@Mapper
public interface ComplainDao {

	int listCount(@Param("params")Map<String, Object> params);

	List<ComplainDTO> list(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);
	
	ComplainDTO getComplain(@Param("id")Integer id);

}
