package com.orhonit.modules.generator.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgMeetFileEntity;

@Mapper
public interface ZgMeetFileDao extends BaseMapper<ZgMeetFileEntity>{

	void saveFile(ZgMeetFileEntity zgMeetFileEntity);
	
}
