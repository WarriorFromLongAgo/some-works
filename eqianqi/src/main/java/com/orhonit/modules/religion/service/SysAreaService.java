package com.orhonit.modules.religion.service;

import java.util.List;


import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.religion.entity.SysArea;

public interface SysAreaService extends IService<SysArea> {
	
	/**
	 * 通过地区名称模糊查询  只有嘎查
	 * @param parentId
	 * @return
	 */
	public List<SysArea> selectAreaGacha();
	
	/**
	 * 通过父级id查询地区信息
	 * @param parentId
	 * @return
	 */
	public List<SysArea> selectAreaByParentId(String parentId);

}
