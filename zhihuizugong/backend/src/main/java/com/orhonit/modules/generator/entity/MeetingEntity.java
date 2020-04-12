package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支部活动表
 * 
 * @author zjw
 * @email chobitsyjwz@163.com
 * @date 2019-02-14 14:56:22
 */
@TableName("tb_meeting")
@Data
public class MeetingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String meetId;
	/**
	 * 会议名称
	 */
	private String meetTitle;
	/**
	 * 会议内容
	 */
	private String meetContent;

	/**
	 * 会议状态0:未开始，1：进行中，2：已结束，3：已取消
	 */
	private Integer meetingStatus;
	/**
	 * 创建人id
	 */
	private Long userId;
	/**
	 * 创建人名称
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private Date crtTime;
	/**
	 * 更新时间
	 */
	private Date updTime;
	/**
	 * 会议部门
	 */
	private Integer deptId;
	/**
	 * 会议单位
	 */
	private Integer orgId;
	/**
	 * 路线id
	 */
	private Integer routeId;
	/**
	 * 会议开始时间
	 */
	private Date meetBeginTime;
	/**
	 * 会议结束时间
	 */
	private Date meetEndTime;
	/**
	 * 会议类型 1 三会一课 2 组织生活会 3 民主生活会 4 党员评议 5 党日活动 6 其他活动
	 */
	private Integer meetType;


}
