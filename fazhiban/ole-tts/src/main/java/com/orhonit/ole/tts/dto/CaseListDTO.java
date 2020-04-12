package com.orhonit.ole.tts.dto;

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
	
	private String caseStatus;
	
	private String zprId;
	
	private String isDeal;
	
	private String deptName;
}
