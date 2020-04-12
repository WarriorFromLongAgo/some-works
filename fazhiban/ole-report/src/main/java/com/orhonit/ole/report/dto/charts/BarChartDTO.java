package com.orhonit.ole.report.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 多条条形图对象类
 * @author Jwen
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarChartDTO {
	
	//x、y轴显示名称
	private String name;
	//x、y轴对应参数
	private String value;
	
	private String value1;
	
	private String value2;
}
