package com.orhonit.ole.enforce.dto.ps;

import lombok.Data;
@Data
public class PsLawDTO {
	private String lawId;
	private String lawName;
	private String pubDept;
	private String lawCount;
	private String itemType;
	private String sourceHref;
	private String potenceName;
	private String effectLevel;
	//权责类型
	private String proType;
	private String deptId;
}
