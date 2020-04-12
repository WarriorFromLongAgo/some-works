package com.orhonit.ole.report.service.cases.Impl;

import com.orhonit.ole.report.dao.cases.ReportCasePersonTypeDao;
import com.orhonit.ole.report.dto.cases.CasePersonTypeDTO;
import com.orhonit.ole.report.service.cases.ReportCasePersonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处罚人员类别占比分析
 * @author Jwen
 *
 */
@Service
public class ReportCasePersonTypeServiceImpl implements ReportCasePersonTypeService {

	@Autowired
	private ReportCasePersonTypeDao reportCasePersonTypeDao;

	@Override
	public List<CasePersonTypeDTO> findCasePersonTypeCount() {
		return reportCasePersonTypeDao.findCasePersonTypeCount();
	}
}
