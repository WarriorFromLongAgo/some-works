package com.orhonit.ole.report.service.index.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.IndexDao;
import com.orhonit.ole.report.service.index.IndexService;

@Service
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	private IndexDao indexDao;
	

	@Override
	public Integer getYearCaseCount() {
		return indexDao.getYearCaseCount();
	}

	@Override
	public Integer getNowCaseAccept() {
		return indexDao.getNowCaseAccept();
	}

	@Override
	public Integer getNowCaseClose() {
		return indexDao.getNowCaseClose();
	}

	@Override
	public Integer getYearWarnInfoCount() {
		return indexDao.getYearWarnInfoCount();
	}

	@Override
	public Integer getNowWarnInfoCount() {
		return indexDao.getNowWarnInfoCount();
	}
	
	

}
