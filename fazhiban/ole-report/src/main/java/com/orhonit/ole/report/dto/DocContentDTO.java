package com.orhonit.ole.report.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class DocContentDTO {
	
	@NotBlank(message="案件编号不能为空")
	private String caseId;
	
	@NotBlank(message="模板编号不能为空")
	private String templateId;
	
	@NotBlank(message="内容不能为空")
	private String value;
}
