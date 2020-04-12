package com.orhonit.modules.generator.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppIdeaThumbsUpEntity;
import com.orhonit.modules.generator.app.service.AppIdeaThumbsUpService;
import com.orhonit.modules.generator.app.service.AppTeamUpIdeaService;
import com.orhonit.modules.sys.controller.AbstractController;


/**
  * 为组工出点子  点赞
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/ideathumbsup")
public class AppIdeaThumbsUpController extends AbstractController{
	
	@Autowired
	AppIdeaThumbsUpService appIdeaThumbsUpService;
	@Autowired
	AppTeamUpIdeaService appTeamUpIdeaService;
	
	
	/**
	  * 点赞
	 * @param appIdeaThumbsUpEntity
	 * @return
	 */
	@RequestMapping("/dianzan")
	public R save(@RequestBody AppIdeaThumbsUpEntity appIdeaThumbsUpEntity) {
		appIdeaThumbsUpEntity.setUserId(getUserId());
		if(appIdeaThumbsUpEntity.getUserId()!=null && appIdeaThumbsUpEntity.getIdeaId()!=null&&
				!appIdeaThumbsUpEntity.getIdeaType().equals(null)) {
			String msg = appIdeaThumbsUpService.selectDianZan(appIdeaThumbsUpEntity);
			return R.ok().put("msg", msg);
		}
		return R.parameterIsNul();
	}
	
	/**
	  * 取消点赞
	 * @param thumbsupId
	 * @return
	 */
	@RequestMapping("/quxiaodianzan")
	public R cancel(@RequestBody AppIdeaThumbsUpEntity appIdeaThumbsUpEntity) {
		appIdeaThumbsUpEntity.setUserId(getUserId());
		if(appIdeaThumbsUpEntity.getIdeaId() >0 && appIdeaThumbsUpEntity.getUserId()>0) {
			String msg=appIdeaThumbsUpService.quxiaodianzan(appIdeaThumbsUpEntity);
			return R.ok().put("msg", msg);
		}
		return R.parameterIsNul();
	}
	

}
