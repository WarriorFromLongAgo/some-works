package com.orhonit.modules.religion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.religion.entity.ReligionPersonGroup;
import com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper;
import com.orhonit.modules.religion.service.ReligionPersonGroupService;

/**
 * 
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Service
public class ReligionPersonGroupServiceImpl extends ServiceImpl<ReligionPersonGroupMapper, ReligionPersonGroup> implements ReligionPersonGroupService {

	@Autowired
	private ReligionPersonGroupMapper groupMapper;
	
	@Override
	public void deleteByReligionSiteId(Long id) {
		groupMapper.deleteByReligionSiteId(id);
	}
	
}
