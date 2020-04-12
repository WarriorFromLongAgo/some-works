package com.orhonit.ole.tts.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class CheckDailyDTO {
	private String id;
	
	private String personId;
	
	private String deptId;
	
    private String assistPersonId;
    
	private String checkedUserId;
	
	private String checkNum;
	
	private Integer dealType; //处理方式
	
	private String checkedUserName;
	
	private String checkTitle;
	
	private String checkMode;
	
	private Date checkedDate;
	
	private String roadName;
	
    private String unitName;
    
	private String registNum;
	
	private String legalPerson;
	
	private String checkId;
	
	private String status;
	
	private String checkObjectType;
	
	private String checkSituation;
	
	private String comment; // 核实意见
	
	private String checkZzfryname;
	
	private String checkFzfryname;
	
	private String createName;
	
	private String createBy;
	
	private String flowKey;
	
	private String handleMode;
	
	private String businessId;
	
	private String assignee;
	
	private String nextAssignee;
	
	
}
