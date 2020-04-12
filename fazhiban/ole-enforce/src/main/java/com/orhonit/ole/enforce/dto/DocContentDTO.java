package com.orhonit.ole.enforce.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class DocContentDTO {
	
	private String id;
	
	@NotBlank(message="案件编号不能为空")
	private String caseId;
	
	@NotBlank(message="模板编号不能为空")
	private String templateId;
	
	@NotBlank(message="内容不能为空")
	private String value;
	
	private String createName;
	
	private String createBy;
	
	private String updateName;
	
	private String updateBy;
}
