package com.orhonit.ole.sys.model;

import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterfaceLogs extends BaseEntity<Long>{
	private static final long serialVersionUID = -7809315432127036583L;
	private String username;
	private String url;
	private String httpMethod;
	private Boolean isSuccess;
	private String startTime;
	private String endTime;
	private String execTime;
	private String params;
	private String result;
	private String userAgent;	
}
