package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.WywNoteEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 悟一悟  笔记记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 10:03:13
 */
@Mapper
public interface WywNoteDao extends BaseMapper<WywNoteEntity> {
	
	
	List<WywNoteEntity>wywlist(@Param("params")Map<String,Object>Params);
	
}
