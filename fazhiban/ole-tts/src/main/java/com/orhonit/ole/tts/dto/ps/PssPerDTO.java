package com.orhonit.ole.tts.dto.ps;

import java.util.List;

import com.orhonit.ole.sys.model.Role;

import lombok.Data;

@Data
public class PssPerDTO {
	private String id;
	
	private String name;
	
	private String certNum;
	
	private String deptId;
	
	private String lawType;
	
	private Integer isEffect;
	
	private List<Role> roles;
}
