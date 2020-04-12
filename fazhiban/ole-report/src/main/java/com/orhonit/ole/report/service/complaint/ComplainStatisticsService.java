package com.orhonit.ole.report.service.complaint;

public interface ComplainStatisticsService {
	
	/**
	 * 统计某年投诉的数量
	 * @param id
	 * @return
	 */
	Integer getComplainCountByYear(int year);

	/**
	 * 统计某年某月投诉数量
	 * @param params
	 * @return 
	 */
	Integer getComplainCountByYearMonth(int year,int month);

}
