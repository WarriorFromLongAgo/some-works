package com.orhonit.ole.tts.dto.api;

import java.util.Date;

import lombok.Data;

@Data
public class ApiCaseInfoDTO {
	private String caseNum;// 案件编号/检查 id
	private String caseName;// 案件名称/检查标题	
	private Date caseTime;// 案发时间/检查开始时间
	private String caseSource; // 案源
	private String caseStatus;// 案件状态/检查状态
	private String flowType;//办案流程： 简易：1 、一般：2 、重大案件：3 、专项检查：4 、日常检查：5
	private String caseAddress;//案发地址
	private String caseApplyDate;//收件时间
	private String briefCaseContent;//案件简要内容
	private String caseHandler;//经办人
	private String caseZpr; //指派人
	private String caseZpdate; //指派时间
	private String caseZzfryid;//主执法人员ID
	private String caseZzfryname;//主执法人员名字
	private String caseFzfryid;//副执法人员ID
	private String caseFzfryname;//副执法人员名字
	private String name; //当事人名称
	private String postCode;//当事人邮编
	private String idCard;//当事人身份证号
	private String sex;//当事人性别
	private String age;//当事人年龄
	private String tel;//当事人电话
	private String address;//当事人地址
	private String unitAddress;//当事人单位地址
	private String unitName;//当事人单位名称
	private String legalName;//法定代表人
	private String orgIdCard;//法人身份证号
	private String orgCode;//组织机构代码
	private String type;//当事人类型 个人:1;组织:2;
}
