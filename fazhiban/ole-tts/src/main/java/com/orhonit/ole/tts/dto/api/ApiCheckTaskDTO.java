package com.orhonit.ole.tts.dto.api;

import lombok.Data;

@Data
public class ApiCheckTaskDTO {
	private String id;
	
	private String checkNum;
	
	private String checkTitle;
	
	private String checkData;
	
	private String checkStatus;
	
	private String assistPerson; //协办人
	
	private String roadName;//检查地址
	
	private String person; //执法人员
}
