package com.orhonit.ole.report.service.lawYear;

import java.util.List;

import com.orhonit.ole.report.dto.LawYearDTO;

public interface LawService {
	//新增法律法规年度统计 
	List<LawYearDTO> selectLaw();
	//新增法律法规名称
	List<LawYearDTO> selectLawName();

}
