package com.orhonit.modules.generator.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppEverybodyShareEntity;


/**
 * 大家来分享
 * @author YaoSC
 *
 */
@Mapper
public interface AppEverybodyShareDao extends BaseMapper<AppEverybodyShareEntity>{
	
	
	@Delete("DELETE FROM everybody_share WHERE share_id=#{shareId}")
	void deleteEveryBodyById(@Param("shareId")Integer shareId);
	
	/**
	 * 所有分享列表
	 * @param params
	 * @return
	 */
	List<AppEverybodyShareEntity>listEvery(@Param("params")Map<String,Object>params);
	/**
	 * 我的分享列表
	 * @param params
	 * @return
	 */
	List<AppEverybodyShareEntity>Mylist(@Param("params")Map<String,Object>params);
	
	void insertEveryBodyShare(AppEverybodyShareEntity appEverybodyShareEntity);
	
	
	@Select("SELECT share_url FROM everybody_share WHERE share_id=#{shareId}")
	AppEverybodyShareEntity selectEverBodyById(@Param("shareId")Integer shareId);
	
	/**
	 * 查询分享详细
	 * @return
	 */
	@Select("select a.share_id,a.share_title,a.share_content,a.share_create_time,a.share_url ,e.user_true_name AS userTrueName from everybody_share a\r\n" + 
			"left join sys_user e on a.share_user_id = e.user_id WHERE a.share_id=#{shareId}")
	AppEverybodyShareEntity selectOne(@Param("shareId")Integer shareId);

}
