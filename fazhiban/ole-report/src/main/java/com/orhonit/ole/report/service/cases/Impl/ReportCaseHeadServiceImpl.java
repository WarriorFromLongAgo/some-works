package com.orhonit.ole.report.service.cases.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.cases.ReportCaseHeadDao;
import com.orhonit.ole.report.dto.cases.CaseHeadDTO;
import com.orhonit.ole.report.service.cases.ReportCaseHeadService;

/**
 * 案件处理结果统计
 * @author Jwen
 *
 */
@Service
public class ReportCaseHeadServiceImpl implements ReportCaseHeadService {
	
	@Autowired
	private ReportCaseHeadDao ReportCaseHeadDao;

	@Override
	public List<CaseHeadDTO> findCaseByCaseStatus(String status) {
		return ReportCaseHeadDao.findCaseByCaseStatus(status);
	}
	
	@Override
	public List<CaseHeadDTO> findCaseByCaseStatus1(String status) {
		return ReportCaseHeadDao.findCaseByCaseStatus1(status);
	}

	@Override
	public List<CaseHeadDTO> findCaseByCaseStatusAndYear(String status, String year) {
		return ReportCaseHeadDao.findCaseByCaseStatusAndYear(status, year);
	}
	
	@Override
	public List<CaseHeadDTO> findCaseByCaseStatusAndYear1(String status, String year) {
		return ReportCaseHeadDao.findCaseByCaseStatusAndYear1(status, year);
	}
	
}
