package com.orhonit.modules.religion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.religion.entity.NationCode;
import com.orhonit.modules.religion.mapper.NationCodeMapper;
import com.orhonit.modules.religion.service.NationCodeService;

@Service
public class NationCodeServiceImpl extends ServiceImpl<NationCodeMapper, NationCode> implements NationCodeService{

	@Autowired
	private NationCodeMapper codeMapper;
	
	@Override
	public List<NationCode> selectNationCodeAll() {
		return codeMapper.selectNationCodeAll();
	}

}
