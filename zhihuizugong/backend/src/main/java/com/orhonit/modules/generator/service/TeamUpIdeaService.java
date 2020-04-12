package com.orhonit.modules.generator.service;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.TeamUpIdeaEntity;

public interface TeamUpIdeaService extends IService<TeamUpIdeaEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	
	void deleteIdeaById(Integer ideaId);

}
