package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDTO {

	private String taskId;
	
	private String taskName;
	
	private String procDefId;
	
	private String procInstId;
	
	private String businessId;
	
	private String currentUser;
	
	private String currentTaskKey;
	
	private Date taskCreateTime;
	
	
}
