package com.orhonit.ole.tts.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.tts.dto.WarnDTO;

@Mapper
public interface WarnPersonDao {
	
	WarnDTO getWarnPersonInfoById(@Param("id")String id);

}
