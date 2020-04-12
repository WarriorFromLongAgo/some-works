package com.orhonit.ole.tts.dto.api;

import java.util.Date;
import lombok.Data;

@Data
public class ApiCaseListDTO {
	private String caseNum;// 案件id/检查 id
	private String caseName;// 案件名称/检查标题	
	private Date caseTime;// 案发时间/检查开始时间
	private String caseSource; // 案源
	private String caseStatus;// 案件状态/检查状态
}
