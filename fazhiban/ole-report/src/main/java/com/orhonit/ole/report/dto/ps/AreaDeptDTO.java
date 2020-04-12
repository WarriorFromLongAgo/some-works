package com.orhonit.ole.report.dto.ps;

import lombok.Data;

@Data
public class AreaDeptDTO {
	
	private String id;
	
	private String areaId;
	
	private String areaName;
	
	private String deptProperty;
	
	private String deptName;
	
	private String shortName;
	
	private String code;
	
	private String address;
	
	private String count;

	@Override
	public String toString() {
		return "PsAreaDeptDTO [id=" + id + ", areaId=" + areaId + ", areaName=" + areaName + ", deptProperty="
				+ deptProperty + ", deptName=" + deptName + ", shortName=" + shortName + ", code=" + code + ", address="
				+ address + ", count=" + count + "]";
	}
	
	
}
