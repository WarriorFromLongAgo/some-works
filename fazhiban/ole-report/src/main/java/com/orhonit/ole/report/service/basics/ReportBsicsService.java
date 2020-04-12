package com.orhonit.ole.report.service.basics;

import java.util.List;

public interface ReportBsicsService {
	
	//查询检查转处罚的数量
	List<Object> getCheckedItem();
	
	
	//查询各种类型的数量和总数的对比
	List<Object> getChecked(String areaId);
}
