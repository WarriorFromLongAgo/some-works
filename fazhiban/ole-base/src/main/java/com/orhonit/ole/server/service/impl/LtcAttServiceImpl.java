package com.orhonit.ole.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.service.LtcAttService;

@Async
@Service
public class LtcAttServiceImpl implements LtcAttService {

	@Autowired
	private LtcAttDao lawAttDao;

	@Override
	public void saveLtc(LtcAtt ltcAtt) {
		lawAttDao.save(ltcAtt);
	}


	@Override
	public void delLtc(String id) {
		lawAttDao.delete(id);
	}

	@Override
	public LtcAtt getLtc(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LtcAtt getLtcById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateLtc(LtcAtt ltcAtt) {
		 lawAttDao.updateLtc(ltcAtt);
	}

}
