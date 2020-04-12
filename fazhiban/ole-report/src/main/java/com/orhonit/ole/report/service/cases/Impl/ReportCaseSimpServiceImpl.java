package com.orhonit.ole.report.service.cases.Impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.cases.ReportCSimpDao;
import com.orhonit.ole.report.dto.cases.CaseSimpDTO;
import com.orhonit.ole.report.service.cases.ReportCaseSimpService;

@Service
public class ReportCaseSimpServiceImpl implements ReportCaseSimpService {
	
	
	@Autowired
	private ReportCSimpDao reportCSDao;

	
	public List<CaseSimpDTO> getCaseCount1() {
		
		return reportCSDao.getCaseCount1();
	}

	
	public List<CaseSimpDTO> getCaseCount(@Param("params") Map<String, Object> params) {
		
		return reportCSDao.getCaseCount(params);
	}

}
