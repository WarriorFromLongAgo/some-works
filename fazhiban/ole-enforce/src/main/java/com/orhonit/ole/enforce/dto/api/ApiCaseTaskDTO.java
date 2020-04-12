package com.orhonit.ole.enforce.dto.api;

import java.util.Date;

import lombok.Data;
@Data
public class ApiCaseTaskDTO {
	private String id;
	
	private String caseNum;
	
	private String flowType;
	
	private String caseName;
	
	private String caseSource;
	
	private String caseZzfryname;
	
	private String caseFzfryname;
	
	private String caseAddress;
	
	private String caseStatus;
	
	private Date caseTime;
	
	private String isYuj;
}
