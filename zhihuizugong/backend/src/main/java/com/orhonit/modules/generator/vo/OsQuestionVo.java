package com.orhonit.modules.generator.vo;

import lombok.Data;

@Data
public class OsQuestionVo {
	
	private String libraryId;      //题库ID
	
	private String libraryTitle;    //题库名称
	
	private String libraryType;     //题库类型
	
	private Integer questionScope;  //分值
	
	private String typeName;        //题库类型名称

}
