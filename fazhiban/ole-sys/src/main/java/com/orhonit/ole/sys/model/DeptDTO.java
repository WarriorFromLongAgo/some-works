package com.orhonit.ole.sys.model;

import lombok.Data;

@Data
public class DeptDTO {
	
	private String id;
	
	private String name;//名称
	
	private String code;
	
	private String parentId;//父Id
	
	private String lawType;//主体性质
	
	private String areaId;//区域Id
	
	private String level;   //级别
	
	private int deptProperty;
	
}
