package com.orhonit.modules.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName("study_classify")
@Data
public class StudyClassifyEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
	
	private String classify;    //线上线下分类
	
	private String  classname;       //线上线下分类名称
	
	
}
