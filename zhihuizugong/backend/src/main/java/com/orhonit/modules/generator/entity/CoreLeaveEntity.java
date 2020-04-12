package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-05-10 15:14:54
 */
@Data
@TableName("core_leave")
public class CoreLeaveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 科室ID
	 */
	private Integer lowerId;
	/**
	 * 科室名称
	 */
	private String lowerName;
	/**
	 * 是否请示领导（1：是 0：否）
	 */
	private Integer whetherType;
	/**
	 * 请假时间
	 */
	private String leaveTime;
	/**
	 * 请假天数
	 */
	private String leaveDays;
	/**
	 * 事由
	 */
	private String leaveThing;

}
