package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DocFlowListDTO {

	private String id;
	private String code;
	private String codeName;
	private String flowType;
	private String flowNode;
	private String needAdd;
	private String isEffect;
	private String updateName;
	private Date updateDate;
}
