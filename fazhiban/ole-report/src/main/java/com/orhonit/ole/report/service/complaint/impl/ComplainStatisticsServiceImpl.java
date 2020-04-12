package com.orhonit.ole.report.service.complaint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportComplainDao;
import com.orhonit.ole.report.service.complaint.ComplainStatisticsService;

@Service
public class ComplainStatisticsServiceImpl implements ComplainStatisticsService {

	@Autowired
	ReportComplainDao reportComplainDao;
	
	@Override
	public Integer getComplainCountByYear(int year) {
		return reportComplainDao.getComplainCountByYear(year);
	}

	@Override
	public Integer getComplainCountByYearMonth(int year, int month) {
		return reportComplainDao.getComplainCountByYearMonth(year,month);
	}

}
