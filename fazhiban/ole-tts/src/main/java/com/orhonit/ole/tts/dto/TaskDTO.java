package com.orhonit.ole.tts.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {

	private Integer jobId;
	
	private String beanName;
	
	private String methodName;
	
	private String params;
	
	private String cronExpression;
	
	private Integer status;
	
	private String remark;
	
	private Date createTime;

}
