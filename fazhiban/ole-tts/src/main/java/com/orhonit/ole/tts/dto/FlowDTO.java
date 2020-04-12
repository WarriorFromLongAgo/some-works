package com.orhonit.ole.tts.dto;

import lombok.Data;

@Data
public class FlowDTO {

private String businessId; // 流程表中记录的业务ID
	
	private String assignee; // 任务当前办理人
	
	private String nextAssignee; // 下一办理人
	
	private String flowKey; // 流程唯一的key
	
	private String comment; // 批注
	
	private String handleMode; // 下一节点如果是排他网管需要指定处理方式
	
	private String dealType; // 业务处理类型
	
	private String dealMode; // 业务处理方式
	
	private String flowDefKey; // 多个流程共用一个监听器时，用来区分业务
	
	private String singleMode;//当流程提交时只有一条线路的时候，此值必填 
	
	private String userName;
	
	private String userNum;

	private String timeDate; // 定时任务中的处理期限, 单位: 天
}
