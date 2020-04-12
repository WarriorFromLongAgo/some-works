package com.orhonit.ole.report.service.political.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportAgeDao;
import com.orhonit.ole.report.dto.political.PoliticalDTO;
import com.orhonit.ole.report.service.political.ReportPoliticalService;

@Service
public class ReportPoliticalServiceImpl implements ReportPoliticalService {

	@Autowired
	private ReportAgeDao ageDao;

	@Override
	public List<PoliticalDTO> getArea() {
		return ageDao.getArea2();
	}
	
	@Override
	public List<PoliticalDTO> getAreaPost(Map<String, Object> params){
		
		return ageDao.getAreaPost2(params);
	}
}


