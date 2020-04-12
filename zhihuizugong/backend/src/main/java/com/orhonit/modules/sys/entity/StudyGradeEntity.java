package com.orhonit.modules.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName("study_grade")
@Data
public class StudyGradeEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
	
	private Long userId;    //学习人id
	
	private String  data;       //学习日期 
	
	private String  classId;       //分类id
	
	private String  grade;   //分数
	
	private String  xgrade;   //折算分数
	
	private String  classifyId;   //分数
	
	
	
}
