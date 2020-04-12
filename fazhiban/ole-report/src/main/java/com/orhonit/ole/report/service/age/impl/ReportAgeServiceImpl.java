package com.orhonit.ole.report.service.age.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportAgeDao;
import com.orhonit.ole.report.dto.age.AgeDTO;
import com.orhonit.ole.report.service.age.ReportAgeService;


@Service
public class ReportAgeServiceImpl implements ReportAgeService {

	@Autowired ReportAgeDao reportAgeDao;
	
	@Override
	public List<AgeDTO> getBrithday() {
		return reportAgeDao.getBirthday();
	}
	
}
