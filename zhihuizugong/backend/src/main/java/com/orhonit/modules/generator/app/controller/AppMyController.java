package com.orhonit.modules.generator.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;



/**
 * Title: APP端 我的
 * @Description: 1,个人信息 2,督办通知未读条数 3,公文通知未读条数  4,会议通知未读条数
 * @author YaoSC
 * @date 2019年7月1日 
 */
@RestController
@RequestMapping("/app/My")
public class AppMyController {
	
	@Autowired
	SysUserService sysUserService;

	
	
	/**
	 * 我的信息
	 * @param userId
	 * @return
	 */
	@GetMapping("/message")
	public R message(Integer userId) {
		if(userId>0) {
			SysUserEntity entity =sysUserService.selectById(userId);
			return R.ok().put("message", entity);
		}else {
			return R.parameterIsNul();
		}
	}
}
