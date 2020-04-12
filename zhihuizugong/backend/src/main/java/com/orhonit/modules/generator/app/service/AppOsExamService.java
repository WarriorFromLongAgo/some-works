package com.orhonit.modules.generator.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.AppOsExamEntity;
import com.orhonit.modules.generator.app.vo.AppOsExamVO;

public interface AppOsExamService extends IService<AppOsExamEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	List<AppOsExamVO> getExamNow();

}
