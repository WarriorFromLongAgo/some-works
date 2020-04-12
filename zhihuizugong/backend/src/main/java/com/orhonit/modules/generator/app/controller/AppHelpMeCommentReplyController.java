package com.orhonit.modules.generator.app.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentReplyEntity;
import com.orhonit.modules.generator.app.service.AppHelpMeCommentReplyService;
import com.orhonit.modules.sys.controller.AbstractController;




/**
 * Title: 回复评论
 * @Description:
 * @author YaoSC
 * @date 2019年6月19日 
 */
@RestController
@RequestMapping("app/helpcocmmentreply")
public class AppHelpMeCommentReplyController extends AbstractController{
	
	
	@Autowired
	AppHelpMeCommentReplyService service;
	
	/**
	 * 评论回复
	 * @param entity
	 * @return
	 */
	@RequestMapping("/save")
	public R save(@RequestBody AppHelpMeCommentReplyEntity entity) {
		entity.setUserId(getUserId().intValue());
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		service.insert(entity);
		return R.ok();
	}
	
	/**
	 * 回复列表
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String,Object>params) {
		PageUtils page = service.queryPage(params);
		return R.ok().put("page", page);
	}

}
