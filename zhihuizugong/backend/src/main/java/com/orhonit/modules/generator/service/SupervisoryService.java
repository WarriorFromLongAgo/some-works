package com.orhonit.modules.generator.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.SupervisoryEntity;

public interface SupervisoryService extends IService<SupervisoryEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	PageUtils getListPage(Map<String,Object>params);
	
	SupervisoryEntity getOneSuper(Integer id);
	
	/**
	  * 查询  考勤，请销假，出差培训，违纪违法统计
	 * @param type
	 * @param userId
	 * @return
	 */
	Map<String,Object> getAllList(String type,Integer page,Integer limit,Integer userId);
	

}
