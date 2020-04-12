package com.orhonit.modules.religion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.religion.entity.SysArea;
import com.orhonit.modules.religion.mapper.SysAreaMapper;
import com.orhonit.modules.religion.service.SysAreaService;

@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService{

	@Autowired
	private SysAreaMapper areaMapper;
	
	@Override
	public List<SysArea> selectAreaByParentId(String parentId) {
		return areaMapper.selectAreaByParentId(parentId);
	}

	@Override
	public List<SysArea> selectAreaGacha() {
		return areaMapper.selectAreaGacha();
	}

}
