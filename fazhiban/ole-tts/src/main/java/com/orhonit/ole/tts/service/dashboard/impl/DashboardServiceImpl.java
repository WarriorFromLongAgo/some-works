package com.orhonit.ole.tts.service.dashboard.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.DashboardDao;
import com.orhonit.ole.tts.dto.CaseWranDTO;
import com.orhonit.ole.tts.entity.DeptEntity;
import com.orhonit.ole.tts.repository.DeptRepository;
import com.orhonit.ole.tts.service.dashboard.DashboardService;
@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	private DashboardDao dashboardDao;
	
	@Autowired
	private DeptRepository deptRepository;

	@Override
	public int getSsyjCount(Map<String, Object> params) {
		params.put("warnType", CommonParameters.WarnType.SSYJ);
		return this.dashboardDao.getCountByWarnType(params);
	}

	@Override
	public int getGcyjCount(Map<String, Object> params) {
		params.put("warnType", CommonParameters.WarnType.GCYJ);
		return this.dashboardDao.getCountByWarnType(params);
	}

	@Override
	public int getSxyjCount(Map<String, Object> params) {
		params.put("warnType", CommonParameters.WarnType.SXYJ);
		return this.dashboardDao.getCountByWarnType(params);
	}

	@Override
	public int getJcxxyjCount(Map<String, Object> params) {
		params.put("warnType", CommonParameters.WarnType.JCXXYJ);
		return this.dashboardDao.getCountByWarnType(params);
	}

	@Override
	public String getDeptsByParentDeptId(String dept_id) {
		return this.dashboardDao.getDeptsByParentDeptId(dept_id);
	}

	@Override
	public DeptEntity getDeptDTO(String dept_id) {
		return this.deptRepository.findOne(dept_id);
	}

	@Override
	public List<CaseWranDTO> findCaseWranCount(String deptIds) {
		return this.dashboardDao.findCaseWranCount(deptIds);
	}

	@Override
	public int haveWarnRead() {
		User user = UserUtil.getCurrentUser();
		return this.dashboardDao.haveWarnRead(user.getUsername());
	}
	
}

