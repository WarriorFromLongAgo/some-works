package com.orhonit.ole.enforce.dto;

import lombok.Data;

@Data
public class CheckDocDTO {
    private String checkId;
	
	private String checkTitle;
	
	private String checkNum;
	
	private String docContentId;
	
	private String templateName;
	
	private String templateId;
	
	private String checkStatus;
}
