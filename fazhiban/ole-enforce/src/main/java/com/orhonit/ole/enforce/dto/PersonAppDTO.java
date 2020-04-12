package com.orhonit.ole.enforce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class PersonAppDTO {
		
	private String id;
	
	private String name;
	
	private String certNum;
	
	private String deptId;
	
	@JsonIgnore
	private Integer isEffect;
	
	private Integer roleId;
	
	private Long userId;
}
