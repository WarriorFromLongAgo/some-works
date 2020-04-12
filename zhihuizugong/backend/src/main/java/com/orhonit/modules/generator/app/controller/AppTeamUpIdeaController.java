package com.orhonit.modules.generator.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppTeamUpIdeaEntity;
import com.orhonit.modules.generator.app.service.AppTeamUpIdeaService;
import com.orhonit.modules.sys.controller.AbstractController;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.service.SysUserService;

/**
  * 我为组工出点子
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/teamipidea")
public class AppTeamUpIdeaController extends AbstractController{
	
	
	@Autowired
	AppTeamUpIdeaService temUpIdeaService;
	@Autowired
	SysUserService sysUserService;
	
	
	/**
	  * 录入点子
	 * @param appTeamUpIdeaEntity
	 * @return
	 */
	@RequestMapping("/ideasave")
	public R ideasave(@RequestBody HashMap<String, Object> maps) {
			AppTeamUpIdeaEntity appTeamUpIdeaEntity = JSONObject.parseObject(JSONObject.toJSONString(maps), AppTeamUpIdeaEntity.class);
			appTeamUpIdeaEntity.setCreateUserId(getUserId().intValue());
			appTeamUpIdeaEntity.setIdeaCreateTime(new Date());
			SysUserEntity user=sysUserService.selectById(appTeamUpIdeaEntity.getCreateUserId());
			appTeamUpIdeaEntity.setLowerId(user.getLowerId());
			appTeamUpIdeaEntity.setLowerName(user.getLowerName());
			temUpIdeaService.insert(appTeamUpIdeaEntity);
			return R.ok();
	}
	
	/**
	  * 分页查询所有点子
	 * @param params
	 * @param userId
	 * @return
	 */
	@RequestMapping("/idealist")
	public R list(@RequestParam Map<String, Object> params) {
		 params.put("userId", getUserId());
		 PageUtils page = temUpIdeaService.queryPage(params);
		 return R.ok().put("page", page);
	}
	
	
	/**
	  * 我的点子
	 * @param params
	 * @param createUserId
	 * @return
	 */
	 //@Login
	 @RequestMapping("/myInfo")
	 public R myInfo(@RequestParam Map<String, Object> params) {
		 Integer createUserId=getUserId().intValue();
		 if(createUserId !=0) {
			 PageUtils page = temUpIdeaService.myIdea(params, createUserId);
			 return R.ok().put("page", page);
		 }
		 return R.parameterIsNul();
	 }

}
