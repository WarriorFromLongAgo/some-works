package com.orhonit.ole.warn.dto;


import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class DocTemplateDTO {

	private String id;
	
	@NotBlank(message="模板名称必填")
	private String name;
	
	@NotBlank(message="内容必填")
	private String content;
	
	@NotBlank(message="代码必填")
	private String code;
	
	private Integer isEffect;
	
	private String createName;
	
	private String 	createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
}
