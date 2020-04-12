package com.orhonit.modules.religion.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.religion.entity.ReligionPersonGroup;

/**
 * 
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
public interface ReligionPersonGroupService extends IService<ReligionPersonGroup> {
	
	
	/**
	 * 通过活动场所id删除所有人员信息
	 * @param id
	 */
	public void deleteByReligionSiteId(Long id);
}
