package com.orhonit.modules.generator.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentReplyEntity;
import com.orhonit.modules.generator.app.service.AppIdeaCommentReplyService;
import com.orhonit.modules.sys.controller.AbstractController;

/**
  *点子   评论回复
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/appideaCommentreply")
public class AppIdeaCommentReplyController  extends AbstractController{
	
	@Autowired
	AppIdeaCommentReplyService appIdeaCommentReplyService;
	
	
	
	/**
	 * 评论回复保存
	 * @param appIdeaCommentReplyEntity
	 * @return
	 */
	@PostMapping("/save")
	public R save(@RequestBody AppIdeaCommentReplyEntity appIdeaCommentReplyEntity) {
		appIdeaCommentReplyEntity.setUserId(getUserId());
		try {
			Long.parseLong(appIdeaCommentReplyEntity.getUserId().toString()); 
		} catch (Exception e) {
			  return R.error();
		}
		appIdeaCommentReplyEntity.setReplyTime(new Date());
		appIdeaCommentReplyService.insert(appIdeaCommentReplyEntity);
		appIdeaCommentReplyService.replyCount(appIdeaCommentReplyEntity.getIdeaCommentId());
		return R.ok();
	}
	

}
