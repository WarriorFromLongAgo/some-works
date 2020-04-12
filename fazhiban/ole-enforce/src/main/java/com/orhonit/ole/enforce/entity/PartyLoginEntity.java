package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_ef_party_login")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyLoginEntity {
	
	@Id
	private String loginName;		//登陆账号
	
	private String loginPassword;	//登陆密码
	
	private Integer personType;		//主体类型
	
	private String createBy;		//创建人登录名称
	
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss")
	private Date createDate;		//创建日期
	
	private String updateBy;		//更新人登录名称
	
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss")
	private Date updateDate;		//更新日期
	
	private String createName;		//创建人名称
	
	private String updateName;		//更新人名称
}
