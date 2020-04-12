package com.orhonit.ole.report.service.nation.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportAgeDao;
import com.orhonit.ole.report.dto.nation.NationDTO;
import com.orhonit.ole.report.service.nation.ReportNationService;

@Service
public class ReportNationServiceImpl implements ReportNationService {

	@Autowired
	private ReportAgeDao ageDao;

	@Override
	public List<NationDTO> getArea() {
		return ageDao.getArea4();
	}
	
	@Override
	public List<NationDTO> getAreaPost(Map<String, Object> params){
		
		return ageDao.getAreaPost4(params);
	}
}


