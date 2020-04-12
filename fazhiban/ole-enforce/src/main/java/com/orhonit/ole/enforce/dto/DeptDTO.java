package com.orhonit.ole.enforce.dto;

import lombok.Data;

@Data
public class DeptDTO {
	
	private String id;
	
	private String name;
	
	private String parent_id;
	
	private String law_type;
	
	private String area_id;
	
	private int dept_property;
}
