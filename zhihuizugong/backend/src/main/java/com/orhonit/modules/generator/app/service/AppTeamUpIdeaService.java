package com.orhonit.modules.generator.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppTeamUpIdeaEntity;

/**
 * 为组工出点子
 * @author YaoSC
 *
 */
public interface AppTeamUpIdeaService extends IService<AppTeamUpIdeaEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	/**
	  * 我的点子
	 * @param params
	 * @param userId
	 * @return
	 */
	PageUtils myIdea(Map<String, Object> params,Integer createUserId);
	
	
	/**
	  * 取消点赞
	 * @param ideaId
	 */
    void cancel(Integer ideaId);

}
