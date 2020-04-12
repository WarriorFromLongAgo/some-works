package com.orhonit.modules.generator.vo;

import lombok.Data;

@Data
public class UserAndOrgVO {
	
	Long userId;    //用户ID
	String userTrueName; //真实姓名
	String userNickname;//身份证号
	//Integer userSex;    //性别
	String orgName ;    //单位名称
	Integer userOrg;     //单位ID
	String mobile;     //手机号
	String userHeadPicture;   //头像地址

}
