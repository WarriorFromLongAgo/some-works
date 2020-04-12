package com.orhonit.ole.tts.dto;


import java.util.Date;
import lombok.Data;

@Data
public class AttachFileDTO {

	private String id;
	
	private String fileName;
	
	private String fileNewName;
	
	private String filePath;
	
	private String fileTitle;
	
	private String caseId;
	
	private String caseNum;
	
	private String caseStatus; // 案件状态	
	
	private String fileType;
	
	private String pdfUrl;
	
	private String createName;
	
	private String 	createBy;
	
	private Date createDate;

}
