package com.orhonit.ole.report.service.index;

import org.springframework.stereotype.Service;


public interface IndexService {
	
	/**
	 * 查询今年案件总数
	 * @return
	 */
	Integer getYearCaseCount();
	
	/**
	 * 查询今天案件受理量
	 * @return
	 */
	Integer getNowCaseAccept();
	
	/**
	 * 查询今天案件结案量
	 * @return
	 */
	Integer getNowCaseClose();
	
	/**
	 * 查询今年预警总数
	 * @return
	 */
	Integer getYearWarnInfoCount();
	
	/**
	 * 查询今日预警总数
	 * @return
	 */
	Integer getNowWarnInfoCount();
	
}
