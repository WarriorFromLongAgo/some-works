package com.orhonit.ole.report.service.age;

import java.util.List;


import com.orhonit.ole.report.dto.age.AgeDTO;



public interface ReportAgeService {
	
	//查询所有区域中各执法主体下的日常见数量
	public List<AgeDTO> getBrithday();
}
