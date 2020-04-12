package com.orhonit.modules.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName("study_user")
@Data
public class StudyUserEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
	
	private String username;    //学习人姓名
	
	private String phone;       //学习人手机号
	
	private String upgrade;     //线上总分数
	
	private String nextgrade;   //线下总分数
	
	private String upresult;    //线上结果
	
	private String nextresult;  //线下结果
	
	private String endresult;  //最终结果
	
	private String assessid;  //学习人类型 干部还是党员 和要求
	
	private String userid;
	
	private String data;
	
}
