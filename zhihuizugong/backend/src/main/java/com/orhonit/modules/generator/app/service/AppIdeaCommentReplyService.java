package com.orhonit.modules.generator.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentReplyEntity;

/**
  * 评论回复
 * @author YaoSC
 *
 */
public interface AppIdeaCommentReplyService extends IService<AppIdeaCommentReplyEntity>{
	
	
	/**
	  * 评论+1
	 * @param ideaCommentId
	 */
	void replyCount(Integer ideaCommentId);

}
