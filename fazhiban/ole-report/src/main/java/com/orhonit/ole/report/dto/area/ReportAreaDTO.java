package com.orhonit.ole.report.dto.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Jwen
 * 区域图表DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportAreaDTO {
	
	//区域名称
	private String name;
	//总数
	private String count;
	//平均案件数
	private Double value;

	
	
}
