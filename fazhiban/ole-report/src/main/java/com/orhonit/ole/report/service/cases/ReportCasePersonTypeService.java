package com.orhonit.ole.report.service.cases;


import com.orhonit.ole.report.dto.cases.CasePersonTypeDTO;

import java.util.List;

public interface ReportCasePersonTypeService {

	//处罚人员类别占比分析
	List<CasePersonTypeDTO> findCasePersonTypeCount();
}
