package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentReplyEntity;

@Mapper
public interface AppHelpMeCommentReplyDao extends BaseMapper<AppHelpMeCommentReplyEntity>{
	
	//根据评论ID查出所有回复
	List<AppHelpMeCommentReplyEntity>selectReplyList(@Param("replyId")int replyId,@Param("beginLimit")int beginLimit,@Param("limit")int limit);
	
	

}
