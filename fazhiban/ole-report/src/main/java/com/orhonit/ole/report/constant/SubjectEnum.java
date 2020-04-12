package com.orhonit.ole.report.constant;

public enum SubjectEnum {
	ADMIN_ORGAN("1","行政机关"),
	AUTHORIZED_UNIT("2","授权单位"),
	ENTRUSTMENT_UNIT("3","委托单位");
	
	private String code;
	private String type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private SubjectEnum(String code, String type) {
		this.code = code;
		this.type = type;
	}
	
	

	
	

	
}
