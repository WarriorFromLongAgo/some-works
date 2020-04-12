package com.orhonit.ole.tts.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orhonit.ole.sys.model.Role;

import lombok.Data;

@Data
public class PersonDTO {

	@JsonIgnore
	private String id;
	
	private String name;
	
	@JsonIgnore
	private String certNum;
	
	private String deptId;
	
	@JsonIgnore
	private Integer isEffect;
	
	private List<Role> roles;
}
