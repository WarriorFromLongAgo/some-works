	package com.orhonit.ole.report.service.cases.Impl;

import com.orhonit.ole.report.dao.cases.ReportWranInfoDao;
import com.orhonit.ole.report.dto.cases.DeptWranDTO;
import com.orhonit.ole.report.service.cases.ReportDeptWranInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 案件处理结果统计
 * @author Jwen
 *
 */
@Service
public class ReportDeptWranInfoServiceImpl implements ReportDeptWranInfoService {

	@Autowired
	private ReportWranInfoDao reportWranInfoDao;


	@Override
	public List<DeptWranDTO> findDeptWranCount(String deptIds) {
		return reportWranInfoDao.findDeptWranCount(deptIds);
	}

	@Override
	public List<DeptWranDTO> findAreaWranCount() {
		return reportWranInfoDao.findAreaWranCount();
	}

	@Override
	public List<Map<String,Object>> findBaseWranCount() {
		return reportWranInfoDao.findBaseWranCount();
	}

	@Override
	public List<Map<String, Object>> findBaseWranDetail(String taskList) {
		taskList = taskList.replaceAll(" ", "");
		return reportWranInfoDao.findBaseWranDetail(taskList);
	}
}
