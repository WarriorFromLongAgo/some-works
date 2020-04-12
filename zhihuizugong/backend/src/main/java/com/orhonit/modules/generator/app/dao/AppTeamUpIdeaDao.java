package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppTeamUpIdeaEntity;


/**
 * 我为组工出点子
 * @author YaoSC
 *
 */
@Mapper
public interface AppTeamUpIdeaDao extends BaseMapper<AppTeamUpIdeaEntity>{
	//根据用户id查询我的点子
	List<AppTeamUpIdeaEntity> myIdea(@Param("createUserId")Integer createUserId,
			@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	

	/**
	  * 点赞+1
	 * @param ideaId
	 */
	@Update("UPDATE idea_team_up_ideas SET idea_zan = idea_zan+1 WHERE idea_id = #{ideaId}")
	void updateNewZan(@Param("ideaId")int ideaId); 
	
	/**
	 * 点赞-1
	 * @param ideaId
	 */
	@Update("UPDATE idea_team_up_ideas SET idea_zan = idea_zan-1 WHERE idea_id = #{ideaId}")
	void cancel(@Param("ideaId")int ideaId);
	/**
	  * 查询所有点子列表
	 * @return
	 */
	List<AppTeamUpIdeaEntity>selectAllList(@Param("beginLimit")int beginLimit, @Param("limit")int limit);
}
