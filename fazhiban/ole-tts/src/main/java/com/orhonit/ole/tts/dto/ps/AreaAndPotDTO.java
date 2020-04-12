package com.orhonit.ole.tts.dto.ps;

import lombok.Data;

@Data
public class AreaAndPotDTO {
//	areaId
//	name
//	potenceCount
	
	private String areaId;
	
	private String name;
	
	private String potenceCount;
	
	//权责名称
	private String potenceName;
	//审批要件
	private String approvalReq;
	//办理时限
	private String limitTime;
	//责任事项
	private String duty;
	//责任事项依据
	private String dutyRef;
	//追责情形及依据
	private String accBasis;
	//备注
	private String remarks;
	//承办机构
	private String deptAgentName;
	//实施主体	
	private String deptName;
	//联系电话
	private String tel;
	//法律依据
	private String lawName;

}
