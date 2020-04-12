package com.orhonit.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.sys.entity.MyrequestEntity;
import com.orhonit.modules.sys.entity.StudyUserEntity;
import com.orhonit.modules.sys.vo.MyrequestEntityVo;

@Mapper
public interface StudyUserDao extends BaseMapper<StudyUserEntity> {

	
}
