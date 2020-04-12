package com.orhonit.ole.tts.service.complain.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.tts.dao.ComplainDao;
import com.orhonit.ole.tts.dto.ComplainDTO;
import com.orhonit.ole.tts.service.complain.ComplainService;
@Service
public class ComplainServiceImpl implements ComplainService {
	
	@Autowired
	ComplainDao complainDao;

	@Override
	public List<ComplainDTO> getList(Map<String, Object> params, Integer start, Integer length) {
		return complainDao.list(params, start, length);
	}

	@Override
	public int getListCount(Map<String, Object> params) {
		return complainDao.listCount(params);
	}

	@Override
	public ComplainDTO getComplain(Integer id) {
		return complainDao.getComplain(id);
	}
	
}
