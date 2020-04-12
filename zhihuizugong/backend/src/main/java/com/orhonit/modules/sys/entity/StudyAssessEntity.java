package com.orhonit.modules.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName("study_assess")
@Data
public class StudyAssessEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
	
	private String upassess;   //线上分数线
	
	private String nextassess; //线下分数线
	
	private String name; //用户类型：党员，干部
	
	
}
