package com.orhonit.modules.generator.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.app.entity.AppHelpMeDianZanEntity;


/**
 * 
 * Title: 帮帮我  点赞接口
 * @Description:
 * @author YaoSC
 * @date 2019年6月19日
 */
public interface AppHelpMeDianZanService extends IService<AppHelpMeDianZanEntity>{
	
	/*
	 * 帖子点赞
	 */
	String selectDianZan(AppHelpMeDianZanEntity entity);
	
	/*
	 *帖子取消点赞 
	 */
	String quxiaodianzan(AppHelpMeDianZanEntity entity);

}
