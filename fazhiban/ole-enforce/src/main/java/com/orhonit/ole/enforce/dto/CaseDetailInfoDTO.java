package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

/**
 * 案件详细Dto,包含案件相关的所有信息
 * @author ebusu
 *
 */
@Data
public class CaseDetailInfoDTO {

	// 案件
	private String caseId;
	private String caseName;
	private String caseSource;
	private String caseAddress;
	private Date caseApplyDate;
	private String briefCaseContent;
	private String caseHandler;
	private String caseZpr;
	private Date caseZpdate;
	private String caseZzfryname;
	private String caseZzfryid;
	private String caseFzfryname;
	private String caseStatus;
	private String caseNum;
	private String caseUpdateDate;
	private String caseType;
	private Date caseTime;
	private String caseReason;
	private String fzContent;
	// 当事人
	private String type;
	private String name;
	private String age;
	private String sex;
	private String tel;
	private String idCard;
	private String postCode;
	private String address;
	private String unitName;
	private String unitAddress;
	private String legalName;
	private String orgIdCard;
	private String orgCode;
	private String checkId;
	private String punishType;//处罚类别
	private String punishTypeName;//处罚类别
	private String punishCash;//处罚金额
	private String punishBill;//处罚凭证
	private String punishBillUrl;

}

