package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 参加人员表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-12 15:00:48
 */
@Data
@TableName("tb_meet_people")
public class MeetPeopleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参会人员表
	 */
	@TableId
	private Integer peopleId;
	/**
	 * 会议id
	 */
	private String meetId;
	/**
	 * 参会人员id

	 */
	private Long userId;
	/**
	 * 人员名称
	 */
	private String userName;
	/**
	 * 是否需要接送，0：不需要1：需要
	 */
	private Integer peopleNeedMeet;
	/**
	 * 阅读状态：0：未读（默认），1：已读
	 */
	private Integer peopleIsRead;
	/**
	 * 签到状态： 0:未签到，1：已签到（默认）
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
	 * 乘车站点id
	 */
	private Integer stationId;
	/**
	 * 留言
	 */
	private String peopleLveMsg;

}
