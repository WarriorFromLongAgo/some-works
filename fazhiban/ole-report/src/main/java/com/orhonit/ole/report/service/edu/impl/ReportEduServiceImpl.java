package com.orhonit.ole.report.service.edu.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportAgeDao;
import com.orhonit.ole.report.dto.edu.EduDTO;
import com.orhonit.ole.report.service.edu.ReportEduService;

@Service
public class ReportEduServiceImpl implements ReportEduService {

	@Autowired
	private ReportAgeDao ageDao;


	@Override
	public List<EduDTO> getArea() {
		return ageDao.getArea1();
	}
	
	@Override
	public List<EduDTO> getAreaPost(Map<String, Object> params){
		
		return ageDao.getAreaPost(params);
	}
}


