package com.orhonit.ole.tts.dto;

import lombok.Data;

@Data
public class PerVerifyInfoDTO {

	private String name; // 名称
	
	private String postCode;// 邮编
	
	private String idCard;//身份证号码
	
	private Integer sex;// 性别
	
	private Integer age; // 年龄
	
	private String tel; //电话
	
	private String address; //地址
	
	private String unitName; //单位名称
	
	private String unitAddress; // 当事人单位地址
	
	private String legalName; // 法定代表人
	 
	private String orgIdCard; //法人身份证号码
	
	private String orgCode; // 组织机构代码证
	
	private Integer type;  // 当事人类型
	
	private Integer submitType; // 暂存0， 提交1
	
	private String comment; // 核实意见
	
	private String caseId; // 案件编号,案件主键
	
	private Integer perVerifyStatus; // 暂存或者提交标记为
	
	private Integer dealType; //处理方式
	
}
