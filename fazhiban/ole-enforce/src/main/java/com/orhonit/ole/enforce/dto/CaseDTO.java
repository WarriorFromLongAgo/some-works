package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

/**
 * 反显case
 * @author 武耀忠
 *
 */
@Data
public class CaseDTO {

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
	private String caseFzfryname;
	private String caseZzfryid;
	private String caseFzfryid;
	private String caseWqryid;
	private String caseWqryname;
	private String caseStatus;
	private String caseNum;
	private String caseUpdateDate;
	private String caseType;
	private Date caseTime;
	private String caseReason;
	private String name;
	private String isPs;		//新增：是否公示

}

