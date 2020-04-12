package com.orhonit.modules.generator.app.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentEntity;
import com.orhonit.modules.generator.app.service.AppHelpMeCommentService;
import com.orhonit.modules.sys.controller.AbstractController;


/**
 * Title: AppHelpMeCommentController.java
 * @Description: 请您帮帮我  一级评论
 * @author YaoSC
 * @date 2019年6月18日 
 */
@RestController
@RequestMapping("app/helpcocmment")
public class AppHelpMeCommentController extends AbstractController{
	
	@Autowired
	AppHelpMeCommentService service;
	
	/**
	 * 一级评论提交
	 * @param entity
	 * @return
	 */
	@PostMapping("/save")
	public R save(@RequestBody AppHelpMeCommentEntity entity) {
		
		entity.setComCreateTime(new Date());
		entity.setComUpdateTime(new Date());
		entity.setComUserId(getUserId().intValue());
		service.insertCommetn(entity);
		return R.ok();
	}
	
	/**
	 * 评论列表
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String,Object>params) {
		if(params.containsKey("comHelpMeId")) {
			 params.put("userId", getUserId());
			 PageUtils page = service.queryPage(params);
			 return R.ok().put("page", page);
		}
		return R.parameterIsNul();
	}
	
	/**
	 * 删除评论
	 * @param comId
	 * @return
	 */
	@RequestMapping("/delete")
	public R delete(Integer comId) {
		service.deleteById(comId);
		return R.ok();
	}
	
	

}
