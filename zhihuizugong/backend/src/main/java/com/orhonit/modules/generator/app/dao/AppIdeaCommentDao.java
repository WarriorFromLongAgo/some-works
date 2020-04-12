package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentEntity;
import com.orhonit.modules.generator.app.vo.AppIdeaCommentVO;


/**
  * 我为组工出点子  评论
 * @author YaoSC
 *
 */
@Mapper
public interface AppIdeaCommentDao extends BaseMapper<AppIdeaCommentVO>{
	
	/**
	  * 点子  评论列表
	 * @param newId
	 * @param beginLimit
	 * @param limit
	 * @return
	 */
	List<AppIdeaCommentVO> getIdeaCommentByPage(@Param("ideaId")Integer ideaId,
			@Param("beginLimit")int beginLimit, @Param("limit")int limit);
    
	/**
	  * 评论+1
	 * @param ideaCommentId
	 */
	void replyCount(@Param("ideaCommentId")Integer ideaCommentId);
	
	/**
	  * 单条评论和其他的评论回复
	 * @param ideaCommentId
	 * @param beginLimit
	 * @param limit
	 * @return
	 */
	AppIdeaCommentVO newComAndReply(@Param("ideaCommentId")Integer ideaCommentId,
			@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	
	@Insert("INSERT INTO idea_comment(idea_comment_content,user_id,idea_id)VALUES(#{ideaCommentContent},#{userId},#{ideaId}) ")
	void insertIdeaComment(AppIdeaCommentEntity entity);
	
	/**
	 * 点子下的评论数量
	 * @param ideaId
	 * @return
	 */
	@Select("SELECT count(*) FROM idea_comment WHERE idea_id=#{ideaId}")
	int countComment(@Param("ideaId")int ideaId);
}
