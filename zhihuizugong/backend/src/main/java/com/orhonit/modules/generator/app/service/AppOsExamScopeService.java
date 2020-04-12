package com.orhonit.modules.generator.app.service;

import java.util.Map;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppOsExamScopeEntity;


/**
 * 答题成绩表
 * @author YaoSC
 *
 */

public interface AppOsExamScopeService extends IService<AppOsExamScopeEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	/**
	 * 个人答题排行榜
	 * @param params
	 * @return
	 */
	PageUtils selectPersonRank(Map<String,Object> params);

}
