package com.orhonit.ole.enforce.dto.ps;

import lombok.Data;

@Data
public class LoginUserDTO {

	private String userName;
	
	private String passward;
	
	private String personType;
	
	private String idCord; //当事人身份证号
	
	private String name;	//当事人名称
	
	private String orgCode;	//组织机构代码证
	
}
