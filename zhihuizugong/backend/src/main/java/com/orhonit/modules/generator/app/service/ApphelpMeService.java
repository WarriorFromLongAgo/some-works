package com.orhonit.modules.generator.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.entity.ApphelpMeEntity;
import com.orhonit.modules.generator.app.vo.AppHelpMeVO;


/**
 * 发帖信息 接口
 * Title: ApphelpMeService.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月13日
 */
public interface ApphelpMeService extends IService<AppHelpMeVO>{
	
	PageUtils queryPage(Map<String, Object> params);
	//删除
	void deleteId(Integer helpMeId);
	//修改
	void updateHelp(ApphelpMeEntity entity);
	//插入一条帖子
	void insertHelpMe(ApphelpMeEntity entity);
}
