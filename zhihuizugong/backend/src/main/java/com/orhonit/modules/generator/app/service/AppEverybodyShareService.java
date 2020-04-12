package com.orhonit.modules.generator.app.service;

import java.util.Map;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppEverybodyShareEntity;

/**
 * 大家分享
 * @author YaoSC
 *
 */
public interface AppEverybodyShareService extends IService<AppEverybodyShareEntity>{
	
	/**
	 * 所有分享列表
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);
	/**
	  * 我的分享列表
	 * @param params
	 * @return
	 */
	PageUtils MylistPage(Map<String,Object> params);

	AppEverybodyShareEntity selectOne (Integer shareId);
	
	void deleteEveryBodyById(Integer shareId);
	
	void insertEveryBody(AppEverybodyShareEntity everybodyShareEntity);
	
	
}
