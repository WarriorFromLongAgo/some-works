package com.orhonit.modules.generator.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.OrganizationWorkshopEntity;


/**
  * 组工讲堂
 * @author YaoSC
 *
 */
public interface OrganizationWorkshopService extends IService<OrganizationWorkshopEntity>{
	
	 PageUtils queryPage(Map<String, Object> params);
	 
	 PageUtils appqueryPage(Map<String, Object> params);
	 
	 PageUtils AppqueryPage(Map<String,Object>params);
	 
	 
	 //APP端组工讲堂 详情
	 OrganizationWorkshopEntity selectWorkShop(Integer courseId);
	 
	 //单条删
	 void deleteWorkShop(Integer courseId);
	 
	 void updateShop(OrganizationWorkshopEntity entity);

}
