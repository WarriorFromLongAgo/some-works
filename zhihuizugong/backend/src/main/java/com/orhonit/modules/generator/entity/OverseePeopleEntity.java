package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 领导督办人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@Data
@TableName("tb_oversee_people")
public class OverseePeopleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 督办人员表
	 */
	@TableId
	private Integer peopleId;
	/**
	 * 督办id
	 */
	private String overseeId;
	/**
	 * 领导督办人员id

	 */
	private Long userId;
	/**
	 * 领导督办人员名称

	 */
	private String peopleName;
	/**
	 * 阅读状态：0：未读（默认），1：已读
	 */
	private Integer peopleIsRead;
	/**
	 * 签到状态： 0:未签到（默认），1：已签到
	 */
	private Integer peopleIsSignin;
	/**
	 * 是否参加： 0：不参加（默认），1：参加
	 */
	private Integer peopleJoin;
	/**
	 * 用户所在支部
	 */
	private Integer deptId;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 创建时间
	 */
	private Date crtTime;
	/**
	 * 留言
	 */
	private String peopleLveMsg;
	/**
	 * 用户所在科室
	 */
	private Integer lowerId;

}
