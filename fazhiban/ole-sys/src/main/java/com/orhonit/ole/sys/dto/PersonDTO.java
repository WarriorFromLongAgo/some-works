package com.orhonit.ole.sys.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orhonit.ole.sys.model.Role;

import lombok.Data;

@Data
public class PersonDTO {

	
	private String id;
	
	private String name;
	
	private String certNum;
	
	private String deptId;
	
	private String deptName;
	
	private String tel;
	
	private String picture;
	
	@JsonIgnore
	private Integer isEffect;
	
	private List<Role> roles;
	
	private Integer delFlag;
}
