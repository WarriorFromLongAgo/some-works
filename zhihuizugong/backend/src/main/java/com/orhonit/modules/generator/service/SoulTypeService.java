package com.orhonit.modules.generator.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.SoulTypeEntity;

public interface SoulTypeService  extends IService<SoulTypeEntity>{
	
	 PageUtils queryPage(Map<String, Object> params);
	 
	 void deleteType(Integer typeId);
	 
	 void updateType(SoulTypeEntity entity);
	 //查出所有分类
	 List<SoulTypeEntity>soulTypeList();
	 

}
