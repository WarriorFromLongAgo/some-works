package com.orhonit.ole.report.service.inspect.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportAgeDao;
import com.orhonit.ole.report.dto.position.PositionDTO;
import com.orhonit.ole.report.service.inspect.ReportPositionService;

@Service
public class ReportPositionServiceImpl implements ReportPositionService {

	@Autowired
	private ReportAgeDao ageDao;


	@Override
	public List<PositionDTO> getArea() {
		return ageDao.getArea3();
	}
	
	@Override
	public List<PositionDTO> getAreaPost(Map<String, Object> params){
		
		return ageDao.getAreaPost3(params);
	}
}


