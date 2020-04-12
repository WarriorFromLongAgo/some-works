package com.orhonit.ole.tts.service.realtime.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.RealTimeDao;
import com.orhonit.ole.tts.dto.CaseDetailInfoDTO;
import com.orhonit.ole.tts.dto.CaseDocDTO;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.DeptDTO;
import com.orhonit.ole.tts.service.realtime.RealTimeService;

@Service
public class RealTimeServiceImpl implements RealTimeService {
	
	@Autowired
	private RealTimeDao realTimeDao;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Override
	public Integer getCasecount(Map<String, Object> params) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.realTimeDao.caseCount(params);
	}
	
	@Override
	public List<CaseListDTO> getCaseList(Map<String, Object> params, Integer start, Integer length) {
		//如果传了deptId，则只查询deptId的内容
		//如果没传deptId，则按法制办/委办局查询
		if(params.get("deptId") == null || "".equals(params.get("deptId"))){
			params.put("deptIds", sysDeptService.getDepts());
		}
		return this.realTimeDao.caseList(params, start, length);
	}

	@Override
	public List<DeptDTO> deptTreeByDeptId(String deptId) {
		return realTimeDao.deptTreeByDeptId(deptId);
	}
	
	@Override
	public List<DeptDTO> deptTreeByAreaId(String deptId) {
		return realTimeDao.deptTreeByAreaId(deptId);
	}
	
	@Override
	public List<DeptDTO> deptTreeAll() {
		return realTimeDao.deptTreeAll();
	}
	@Override
	public CaseDetailInfoDTO queryCaseByCaseId(String caseId, Map<String, Object> params) {
		return this.realTimeDao.getCaseDetailInfo(caseId, params);
	}
	
	@Override
	public List<CaseDocDTO> queryDocContentByCaseId(String caseId) {
		return this.realTimeDao.findCaseDoc(caseId);
	}
}
