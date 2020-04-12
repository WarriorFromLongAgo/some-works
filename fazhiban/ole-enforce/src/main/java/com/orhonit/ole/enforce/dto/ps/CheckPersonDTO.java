package com.orhonit.ole.enforce.dto.ps;

import java.util.List;

import com.orhonit.ole.sys.model.Role;

import lombok.Data;

@Data
public class CheckPersonDTO {
	
	private String id;
	
	private String code;
	
	private String name;
	
	private String sex;
	
	private String nation;
	
	private String tel;
	
	private String political;
	
	private String birthday;
	
	private String edu;
	
	private String cardum;
	
	private String picture;
	
	private String duty;
	
	private String deptId;
	
	private String certNum;
	
	private String nameMgl;
	
	private List<Role> roles;
}
