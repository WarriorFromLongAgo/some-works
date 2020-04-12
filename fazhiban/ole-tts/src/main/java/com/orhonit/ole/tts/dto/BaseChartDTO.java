package com.orhonit.ole.tts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础单条条形图对象类
 * @author Jwen
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseChartDTO {
	
	//x、y轴显示名称
	private String name;
	//x、y轴对应参数
	private String value;
}
