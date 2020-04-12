package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.OverseeFileEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 领导督办附件表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@Mapper
public interface OverseeFileDao extends BaseMapper<OverseeFileEntity> {

	//文档附件列表
	List<OverseeFileEntity> wordList(@Param("overseeId")String overseeId);
	//图片附件列表
	List<OverseeFileEntity> imageList(@Param("overseeId")String overseeId);
	//音频附件列表
	List<OverseeFileEntity> audioList(@Param("overseeId")String overseeId);
	//视频附件列表
	List<OverseeFileEntity> videoList(@Param("overseeId")String overseeId);
	
}
