package com.orhonit.modules.religion.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.orhonit.modules.religion.entity.ReligionPerson;
import com.orhonit.modules.religion.mapper.ReligionPersonMapper;
import com.orhonit.modules.religion.service.ReligionPersonService;

/**
 * 民族人员
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Service
public class ReligionPersonServiceImpl  extends ServiceImpl<ReligionPersonMapper, ReligionPerson> implements  ReligionPersonService{
	
	@Autowired
	private ReligionPersonMapper personMapper;

	@Override
	public Map<String, Object> selectReligionPersonAll(Map<String, Object> params) {
		PageHelper.startPage((int)params.get("page"),(int) params.get("pageSize"));
		List<ReligionPerson> list = personMapper.selectReligionPersonAll(params);
		
		List<ReligionPerson> listSize = personMapper.selectReligionPersonAll(params);
		params.clear();
		params.put("size", listSize.size());
		params.put("list", list);
		return params;
	}
	
}
