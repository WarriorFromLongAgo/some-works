package com.orhonit.ole.enforce.dto;

import lombok.Data;

@Data
public class CaseDetailDTO {
	
	private String caseId;
	
	private String caseNum;
	
	private String caseName;
	
	private String caseSource;
	//收件时间
	private String caseApplyDate;
	
	private String caseAddress;
	//案件简要内容
	private String briefCaseContent;
	//经办人
	private String caseHandler;
	//当事人类型
	private String type;
	//当事人姓名
	private String name;
	//当事人年龄
	private String age;
	private String sex;
	private String tel;
	//当事人身份号
	private String idCard;
	private String postCode;
	private String address;
	private String unitName;
	private String unitAddress;
	//法定代表人
	private String legalName;
	//法人身份证号
	private String orgIdCard;
	//组织机构代码
	private String orgCode;
}
