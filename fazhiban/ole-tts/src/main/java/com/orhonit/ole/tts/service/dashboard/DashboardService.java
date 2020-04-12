package com.orhonit.ole.tts.service.dashboard;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.CaseWranDTO;
import com.orhonit.ole.tts.entity.DeptEntity;

public interface DashboardService {

	int getSsyjCount(Map<String, Object> params);

	int getGcyjCount(Map<String, Object> params);

	int getSxyjCount(Map<String, Object> params);

	int getJcxxyjCount(Map<String, Object> params);

	String getDeptsByParentDeptId(String dept_id);

	DeptEntity getDeptDTO(String dept_id);

	List<CaseWranDTO> findCaseWranCount(String deptIds);

	int haveWarnRead();
	
}
