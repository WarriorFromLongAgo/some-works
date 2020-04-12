package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserEntity {

	@Id
	private Integer id;

	private String username;//用户名
	private String password;//密码
	private String salt;//盐值
	private String nickname;//昵称
	private String phone;//手机号码
	private String telephone;//联系电话
	private String email;//邮箱
	private Date birthday;//生日
//	private int sex;//性别
	private int status;//状态
	private String areaId;//区划ID
	private String deptId;//主体ID
	private String personId;//人员ID
}
