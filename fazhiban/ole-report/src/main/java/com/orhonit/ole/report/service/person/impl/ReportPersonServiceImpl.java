package com.orhonit.ole.report.service.person.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportPersonDao;
import com.orhonit.ole.report.dto.PersonDTO;
import com.orhonit.ole.report.service.person.ReportPersonService;


@Service
public class ReportPersonServiceImpl implements ReportPersonService {

	@Autowired
	private ReportPersonDao reportPersonDao;
	
	@Override
	public List<PersonDTO> getAreaDeptCheck() {
		return reportPersonDao.getAreaDeptCheck();
	}
	
	@Override
	public List<PersonDTO> getAreaDeptCheckDaily() {
		return reportPersonDao.getAreaDeptCheckDaily();
	}

	@Override
	public List<PersonDTO> getAreaDeptCheckByAreaName(Map<String, Object> params) {
		
		return reportPersonDao.getAreaDeptCheckByAreaName(params);
	}



}
