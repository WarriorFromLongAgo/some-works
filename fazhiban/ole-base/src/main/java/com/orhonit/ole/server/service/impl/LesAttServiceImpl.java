package com.orhonit.ole.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.Lesection;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.service.LesAttService;
import com.orhonit.ole.server.service.LtcAttService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@Async
@Service
public class LesAttServiceImpl implements LesAttService {

	@Autowired
	private LtcAttDao lawAttDao;

	@Override
	public int saveSection(Lesection lesection) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Lesection updateSection(Lesection lesection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delSection(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Lesection getSection(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lesection getSectionById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
