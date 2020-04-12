package com.orhonit.modules.generator.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentReplyEntity;

public interface AppHelpMeCommentReplyService extends IService<AppHelpMeCommentReplyEntity>{
	
	PageUtils queryPage(Map<String, Object> params);

}
