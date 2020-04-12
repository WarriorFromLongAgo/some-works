package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 领导督办主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@Data
@TableName("tb_leadership_oversee")
public class LeadershipOverseeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String overseeId;
	/**
	 * 督办名称
	 */
	private String overseeTitle;
	/**
	 * 督办内容
	 */
	private String overseeContent;
	/**
	 * 领导督办状态0:未开始，1：进行中，2：已结束，3：已取消
	 */
	private Integer overseeStatus;
	/**
	 * 创建人id
	 */
	private Long userId;
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
	 * 录音地址
	 */
	private String recordPath;
	/**
	 * 完成时限
	 */
	private Date completeTime;
	/**
	 * 科室ID
	 */
	private Integer lowerId;

}
