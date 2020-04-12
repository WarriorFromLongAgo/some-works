package com.orhonit.ole.report.service.power;

import java.util.List;

import com.orhonit.ole.report.dto.power.PowerAndRespDTO;

public interface PowerAndRespService {
	
	//获取所有执法部门的职权数量
	List<PowerAndRespDTO> getCount();
	
	//获取本年度各个执法性质的行政执法权数
	List<PowerAndRespDTO> getClassFica();
	
	//获取本年度各个部门总的行政执法权数
	List<PowerAndRespDTO> getDepartCount(String areaId);
	
	//获取各个部门的日常检查数量
	List<PowerAndRespDTO> getDayInspection(String areaId);
}
