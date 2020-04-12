package com.orhonit.ole.report.dto.cases;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Jwen
 * 案件发生热点图DTO
 */
public class CaseHotMapDTO {

	private String caseTime;
	
	private String count;
	
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss") 
	public String getCaseTime() {
		return caseTime;
	}
	
	public void setCaseTime(String caseTime) {
		this.caseTime = caseTime;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	
}
