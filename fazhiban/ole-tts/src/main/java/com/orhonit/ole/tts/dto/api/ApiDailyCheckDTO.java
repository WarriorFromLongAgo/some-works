package com.orhonit.ole.tts.dto.api;

import java.util.Date;

import lombok.Data;

@Data
public class ApiDailyCheckDTO {
	
	private String id ; // 检查内部编号
	
	private String checkNum; //检查编号
	
	private String checkTitle; //检查标题
	
	private String checkMode; // 检查类型
	
	private String sistPersonName; // 协办人
	
	private String personName; //执法人
	
	private String roadName; // 街道名称
	
	private Date checkedDate; // 检查时间
	
	private String checkStatus; // 检查状态
	
	private String checkSituation; // 巡查情况
	
	private String checkObjectType ; //检查对象类型
	
	private String checkedUserName; // 姓名
	
	private String checkedUserId ; //身份证号
	
	private String unitName; // 单位名称
	
	private String registNum; //注册号
	
	private String legalPerson; // 法人
	
	private String flowType; // 流程类型

}
