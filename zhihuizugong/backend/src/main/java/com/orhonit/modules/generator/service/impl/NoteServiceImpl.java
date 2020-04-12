package com.orhonit.modules.generator.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.dao.NoteDao;
import com.orhonit.modules.generator.entity.NoteEntity;
import com.orhonit.modules.generator.service.NoteService;


@Service("NoteService")
public class NoteServiceImpl extends ServiceImpl<NoteDao, NoteEntity> implements NoteService {
	
	
	
	@Autowired
	NoteService service;

	@Override
	public void save(NoteEntity entity) {
		entity.setCreateTime(new Date());
		service.insert(entity);
		
	}

	
}
