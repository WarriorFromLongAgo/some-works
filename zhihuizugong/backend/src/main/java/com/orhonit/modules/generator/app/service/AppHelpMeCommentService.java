package com.orhonit.modules.generator.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentEntity;
import com.orhonit.modules.generator.app.vo.AppHelpMeCommentVO;


/**
 * 一级评论
 * Title: AppHelpMeCommentService.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月18日
 */
public interface AppHelpMeCommentService extends IService<AppHelpMeCommentVO>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	void insertCommetn(AppHelpMeCommentEntity entity);

}
