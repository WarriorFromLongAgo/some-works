package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreWorkEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 民心连心桥表
 * 
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
@Mapper
public interface CoreWorkDao extends BaseMapper<CoreWorkEntity> {
	
	/*
	 * @Description:新建一条工作队
	 */
	boolean save(CoreWorkEntity entity);

	@Select("select * from core_work where serve_id = #{serveId}")
	CoreWorkEntity selectByServeId(String serveId);

	@Delete("delete from core_work where serve_id = #{serveId}")
	void deleteByServeId(String serveId);
	
}
