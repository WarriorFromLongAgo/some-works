package com.orhonit.ole.tts.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TaskResultDTO {

	private String personId;
	
	private String content;
	
	private Date createDate;
	
	private String recordId;
	
	private String recordCode;
	
	private String deptId;
	
	private Integer type;
	
	private Integer taskId;
	
	private Integer level;
}
