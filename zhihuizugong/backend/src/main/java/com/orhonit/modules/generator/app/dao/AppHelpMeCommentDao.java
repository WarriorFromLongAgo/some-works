package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentEntity;
import com.orhonit.modules.generator.app.vo.AppHelpMeCommentVO;


/***
 * 一级评论
 * Title: AppHelpMeCommentDao.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月18日
 */
@Mapper
public interface AppHelpMeCommentDao extends BaseMapper<AppHelpMeCommentVO>{
	
	/*
	 * 一级评论列表
	 */
	List<AppHelpMeCommentVO>selectCommentList(@Param("comHelpMeId")Integer comHelpMeId,
			@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	
	/**
	 * 插入一条评论
	 * @param entity
	 */
	@Insert("INSERT INTO please_help_com_comment \r\n" + 
			"(com_content,com_create_time,com_update_time,com_help_me_id,com_user_id\r\n" + 
			")value(\r\n" + 
			"#{comContent},#{comCreateTime},#{comUpdateTime},#{comHelpMeId},#{comUserId}\r\n" + 
			")")
	void insertCommetn(AppHelpMeCommentEntity entity);
	/*
	 * 统计帖子下的所有评论数量
	 */
	@Select("SELECT count(*) FROM please_help_com_comment WHERE com_help_me_id=#{helpMeId}")
	int selectCountZan(@Param("helpMeId")int helpMeId);

}
