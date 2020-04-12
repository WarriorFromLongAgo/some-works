package com.orhonit.modules.generator.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.app.entity.AppIdeaThumbsUpEntity;

/**
  * 为组工出点子  点赞
 * @author YaoSC
 *
 */
public interface AppIdeaThumbsUpService extends IService<AppIdeaThumbsUpEntity>{
	
	
	/**
	  * 点赞+1
	 * @param ideaId
	 */
	void updateNewZan(Integer ideaId);
	
	/**
	 * 点赞
	 * @param entity
	 * @return
	 */
	String selectDianZan(AppIdeaThumbsUpEntity entity);
	
	/**
	 * 取消点赞
	 * @param entity
	 * @return
	 */
	String quxiaodianzan(AppIdeaThumbsUpEntity entity);
	
	/**
	  * 点赞-1
	 * @param thumbsupId
	 */
	void deleteZan(Integer thumbsupId);
	
	

}
