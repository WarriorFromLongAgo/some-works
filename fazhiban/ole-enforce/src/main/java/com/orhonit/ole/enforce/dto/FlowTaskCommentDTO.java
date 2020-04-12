package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class FlowTaskCommentDTO {

	private String user;
	
	private String taskId;
	
	private String processInstanceId;
	
	private Date time;
	
	private String msg;
	
	private String taskName;
}
