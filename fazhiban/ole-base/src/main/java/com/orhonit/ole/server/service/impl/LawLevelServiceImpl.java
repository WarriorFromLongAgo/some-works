package com.orhonit.ole.server.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orhonit.ole.server.dao.LawLevelDao;
import com.orhonit.ole.server.model.LawLevel;
import com.orhonit.ole.server.service.LawLevelService;

@Service
public class LawLevelServiceImpl implements LawLevelService {
	
	@Autowired
	private LawLevelDao lawLevelDao;

	@Override
	public List<LawLevel> list(Map<String, Object> param, Integer start, Integer length) {
		return this.lawLevelDao.list(param,start,length);
	}

	@Override
	public String getNameById(String parentId) {
		return this.lawLevelDao.getNameById(parentId);
	}
	
	@Override
	public String getMglNameById(String parentId) {
		return this.lawLevelDao.getMglNameById(parentId);
	}

	@Override
	public int count(Map<String, Object> param) {
		return this.lawLevelDao.count(param);
	}

	@Override
	public List<LawLevel> getAllLawLevel() {
		return this.lawLevelDao.getAllLawLevel();
	}

	@Override
	public void save(LawLevel lawLevel) {
		this.lawLevelDao.save(lawLevel);
	}

	@Override
	public void updateDel(String id) {
		this.lawLevelDao.updateDel(id);
	}

	@Override
	public LawLevel getInfoById(String id) {
		return this.lawLevelDao.getInfoById(id);
	}

	@Override
	public void update(LawLevel lawLevel) {
		this.lawLevelDao.update(lawLevel);
	}
	
	
}
