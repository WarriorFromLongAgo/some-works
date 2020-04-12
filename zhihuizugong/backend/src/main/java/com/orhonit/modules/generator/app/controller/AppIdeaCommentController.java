package com.orhonit.modules.generator.app.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentEntity;
import com.orhonit.modules.generator.app.service.AppIdeaCommentService;


/**
  * 点子评论
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/appideacomment")
public class AppIdeaCommentController {
	@Autowired
	AppIdeaCommentService appIdeaCommentService;
	
	
	/**
	  * 评论添加
	 * @param appIdeaCommentEntity
	 * @return
	 */
	@PostMapping("/save")
	public R save(@RequestBody AppIdeaCommentEntity appIdeaCommentEntity) {
		appIdeaCommentService.insertideaComment(appIdeaCommentEntity);
		return R.ok();
	}
	
	
	/**
	  * 点子 评论列表
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String,Object>params) {
		if(params.containsKey("ideaId")) {
			PageUtils page=appIdeaCommentService.commentPage(params);
			return R.ok().put("page", page);
		}
		return R.parameterIsNul();
	}
	
	
	/**
	  * 单条评论和他的评论回复
	 * @param params
	 * @return
	 */
	@GetMapping("/info")
	public R newComAndReply(@RequestParam Map<String, Object> params){
    	if(StringUtils.isNotBlank(params.get("ideaCommentId").toString())) {
    		return R.ok().put("ideaCommentAndReply", appIdeaCommentService.newComAndReply(params));     
    	}
    	return R.parameterIsNul();
    }

}
