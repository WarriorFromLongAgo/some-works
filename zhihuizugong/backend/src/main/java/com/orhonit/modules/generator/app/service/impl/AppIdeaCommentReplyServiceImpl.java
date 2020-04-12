package com.orhonit.modules.generator.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.app.dao.AppIdeaCommentDao;
import com.orhonit.modules.generator.app.dao.AppIdeaCommentReplyDao;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentReplyEntity;
import com.orhonit.modules.generator.app.service.AppIdeaCommentReplyService;


/**
  *点子   评论回复
 * @author YaoSC
 *
 */
@Service("AppIdeaCommentReplyService")
public class AppIdeaCommentReplyServiceImpl extends ServiceImpl<AppIdeaCommentReplyDao,AppIdeaCommentReplyEntity>
                   implements AppIdeaCommentReplyService{

	
	@Autowired
	AppIdeaCommentDao appIdeaCommentDao;
	
	/**
	  * 评论+1
	 */
	@Override
	public void replyCount(Integer ideaCommentId) {
		appIdeaCommentDao.replyCount(ideaCommentId);
		
	}

}
