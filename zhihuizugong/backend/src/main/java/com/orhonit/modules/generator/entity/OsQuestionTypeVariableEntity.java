package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * 题库类型变量表
 * @author 
 *
 */
@Data
@TableName("os_question_type_variable")
public class OsQuestionTypeVariableEntity {
	
	private String typeId;        
	
	private Integer typeVariable;  //值
	
	private String typeName;     //类型名称
	
	
	

}
