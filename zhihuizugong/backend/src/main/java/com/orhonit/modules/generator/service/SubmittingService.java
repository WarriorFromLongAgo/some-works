package com.orhonit.modules.generator.service;

import java.io.IOException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.SubmittingEntity;
import com.orhonit.modules.generator.vo.SubmittingVO;

public interface SubmittingService extends IService<SubmittingEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	Map<String,Object>map(Integer id) throws IOException;
	
	/**
	 *   更新报送单位以及员工信息的 isDel值为1
	 * @param id
	 */
	void updateSubitAndVisory(Integer id);
	
	SubmittingVO getOneSubmit(Integer id);
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	PageUtils submitListPage(Map<String,Object>params);
	

}
