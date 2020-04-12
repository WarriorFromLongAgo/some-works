package com.orhonit.modules.generator.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppWywNoteEntity;

public interface AppWywNoteService extends IService<AppWywNoteEntity> {
	
	PageUtils queryPage(Map<String, Object> params);
	
	PageUtils phPage(Map<String,Object> params);
	
	AppWywNoteEntity getOneWyw(Integer noteId);

}
