package com.orhonit.ole.tts.dto;

import java.util.Date;

import lombok.Data;

/**
 * 流程审批意见
 * @author ebusu
 *
 */
@Data
public class FlowCommentDTO {

	private String user;
	
	private String taskId;
	
	private String  message;
	
	private Date time;
}
