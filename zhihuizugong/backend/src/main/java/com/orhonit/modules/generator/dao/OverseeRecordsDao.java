package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.OverseeRecordsEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 领导督办记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-09 11:14:32
 */
@Mapper
public interface OverseeRecordsDao extends BaseMapper<OverseeRecordsEntity> {
//	领导批示列表
	List<OverseeRecordsEntity> instructionsList(@Param("overseeId")String overseeId);
//	完成进度列表
	List<OverseeRecordsEntity> reviewList(@Param("overseeId")String overseeId);
//	领导点评列表
	List<OverseeRecordsEntity> scheduleList(@Param("overseeId")String overseeId);
	
}
