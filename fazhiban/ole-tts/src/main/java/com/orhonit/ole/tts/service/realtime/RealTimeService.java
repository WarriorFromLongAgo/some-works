package com.orhonit.ole.tts.service.realtime;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.dto.CaseDetailInfoDTO;
import com.orhonit.ole.tts.dto.CaseDocDTO;
import com.orhonit.ole.tts.dto.CaseListDTO;
import com.orhonit.ole.tts.dto.DeptDTO;

public interface RealTimeService {

	Integer getCasecount(Map<String, Object> params);

	List<CaseListDTO> getCaseList(Map<String, Object> params, Integer start, Integer length);

	List<DeptDTO> deptTreeByDeptId(String deptId);

	CaseDetailInfoDTO queryCaseByCaseId(String caseId, Map<String, Object> params);

	List<CaseDocDTO> queryDocContentByCaseId(String caseId);

	List<DeptDTO> deptTreeByAreaId(String deptId);

	List<DeptDTO> deptTreeAll();

}
