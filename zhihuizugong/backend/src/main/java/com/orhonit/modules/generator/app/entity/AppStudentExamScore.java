package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;

import lombok.Data;


/**
 * 学员考试卷面成绩
 * @author YaoSC
 *
 */
@Data
public class AppStudentExamScore implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	// 用户id
	private String userId;
	
	// 用户名
	private String name;
	
	// 试卷编号
	private String paperNo;
	
	// 试卷名称或者题库名称
	private String examName;
	
	// 正确数
	private Integer right;
	
	// 错误数
	private Integer error;
	
	// 成绩
	private Integer score;

}
