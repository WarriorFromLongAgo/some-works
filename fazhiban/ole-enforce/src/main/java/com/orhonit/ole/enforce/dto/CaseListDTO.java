package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CaseListDTO {
	private String id;
	
	private String caseNum;
	
	private String caseName;
	
	private String caseSource;
	
	private String caseAddress;
	
	private Date caseTime;
	
	private Date caseApplyDate;
	
	private Date caseZpDate;
	
	private String caseHandler;
	
	private String zzfryName;
	
	private String fzfryName;
	
	private String caseStatus;
	
	private String zprId;
	
	private String userName;
	
	private String userId;
	
	private String userNum;
	
	private String epiId;
	
	private String deptId;
	
	private String deptName;
	
	private String flowType;
	
	private String caseFzfryName;
	private String caseZzfryName;
}
