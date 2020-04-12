package com.orhonit.ole.enforce.dto.api;

import java.util.Date;
import lombok.Data;

@Data
public class ApiCaseListDTO {
	private String caseNum;// 案件id/检查 id
	private String caseName;// 案件名称/检查标题	
	private String flowType;// 流程类型	
	private Date caseTime;// 案发时间/检查开始时间
	private String caseSource; // 案源
	private String caseStatus;// 案件状态/检查状态
	private String caseFzfryname;//协办人
	private String caseZzfryname;//主执法人
	private String caseAddress;//案发地址
	private String isYuj;//isYuj
}
