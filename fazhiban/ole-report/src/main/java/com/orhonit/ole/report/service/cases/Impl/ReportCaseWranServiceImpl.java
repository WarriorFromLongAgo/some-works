package com.orhonit.ole.report.service.cases.Impl;

import com.orhonit.ole.report.dao.cases.ReportCaseWranDao;
import com.orhonit.ole.report.dto.cases.CaseWranDTO;
import com.orhonit.ole.report.service.cases.ReportCaseWranService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 案件异常统计
 * @author Jwen
 *
 */
@Service
public class ReportCaseWranServiceImpl implements ReportCaseWranService {

	@Autowired
	private ReportCaseWranDao reportCaseWranDao;


	@Override
	public CaseWranDTO findCaseCountByCaseStatus(String num) {
		return reportCaseWranDao.findCaseCountByCaseStatus(num);
	}

	@Override
	public CaseWranDTO findCaseCount() {
		CaseWranDTO caseCount = reportCaseWranDao.findCaseCount();
		caseCount.setName("案件总数");
		return caseCount;
	}

	@Override
	public List<Map<String, Object>> getDeptCaseWarn(Map<String, Object> params) {
		return reportCaseWranDao.getDeptCaseWarn(params);
	}

	@Override
	public List<CaseWranDTO> findCaseWranCount(String depts) {
		return reportCaseWranDao.findCaseWranCount(depts);
	}
}
