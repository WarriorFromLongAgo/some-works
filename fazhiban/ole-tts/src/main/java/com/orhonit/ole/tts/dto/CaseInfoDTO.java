package com.orhonit.ole.tts.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CaseInfoDTO {
	
	private String id;
	
	@NotEmpty(message="案件名称不能为空")
	private String caseName; // 案件名称
	
	@NotEmpty(message="案源不能为空")
	private String caseSource; // 字代表选择
	
	
	private String caseAddress; // 案件地址
	
	private Double lng; // 不填
	
	private Double lat; // 不填
	
	private Date caseApplyDate; // 收件时间 
	
	private String briefCaseContent; // 简要内容
	
	private String caseHandler; // 当前登录人
	
	private String caseZpr; // 案件指派人
	
	private Date caseZpdate; // 当前时间-指派时间
	
	private String caseZzfryid; // 选择执法人员- 当前登录人
	
	private String caseZzfryname; // 主执法人员姓名
	
	private String caseFzfryid; // 选择执法人员
	
	private String caseFzfryname;// 选择副执法人员
	
	private String caseNum; // Util生成案件编号
	
	private String caseType; // 字典选择案件类别
	
	private String deptId; // 当前登录人所在的执法主体
	
	private String caseReason; // 不填
	
	private Date caseTime; // 案发时间,选择
	
	private Integer caseStatus; // 案件状态
	
	private String createName;
	
	private String comment;
	
	private String limitValue;
	
	private String limitType;
	
	private String potenceId;
	
	private String potenceLawMapId;
	
	private String checkId;
	
	private String isPs; // 是否公示
}
