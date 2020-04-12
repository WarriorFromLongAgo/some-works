package com.orhonit.ole.report.service.cases.Impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.cases.ReportCaseHotMapDao;
import com.orhonit.ole.report.dto.cases.CaseHotMapDTO;
import com.orhonit.ole.report.service.cases.ReportCaseHotMapService;


/**
 * 
 * @author Jwen
 * 案件发生热点图
 */
@Service
public class ReportCaseHotMapServiceImpl implements ReportCaseHotMapService{

	@Autowired
	private ReportCaseHotMapDao reportCaseHotMapDao;
	
	@Override
	public List<CaseHotMapDTO> findDailyCase() {
		return reportCaseHotMapDao.findDailyCase();
	}

	@Override
	public List<String> findCaseYearSort() {
		List<String> caseyear = reportCaseHotMapDao.findCaseyear();
		Collections.sort(caseyear);
		return caseyear;
	}

}
