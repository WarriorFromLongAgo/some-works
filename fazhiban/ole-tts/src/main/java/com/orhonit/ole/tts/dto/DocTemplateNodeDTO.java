package com.orhonit.ole.tts.dto;

import lombok.Data;

@Data
public class DocTemplateNodeDTO {
	// 输入框类型
	private String type;
	
	// 对应输入框文字描述
	private String label;
	
	// 内容
	private String value;
	
	// css样式
	private String css;
	
	//classes html calss名列表
	private String[] classes;
	
	// 是否在最终的详细页面中显示该标签内容
	private String isShow;
	
	// 与ref一块使用
	private String id;
	
	// ref引用，引用id中的内容
	private String ref;
	
	// 具体使用ref中的哪个字段
	private String element;
	
	// 值得两边填充空格, css = underline时起作用
	private String padBlankNum;
	
	// 文本输入框长度
	private String maxLength;
	
	// 字典大类值-当type为select时选用
	private String dict;
	
	// 清单类文书的tableId
	private String tableId;
	
	// 清单类文书的列数
	private String columnCount;
	
	// 清单类文书的各个列对应的value
	private String[] tableHeaders;
	
	// 清单类文书的各个列对应的value
	private String[] lineValues;
	
	// 日期格式
	private String dateFormat;
	
	// 是否必填, 1是 , 0否
	private String required;
}
