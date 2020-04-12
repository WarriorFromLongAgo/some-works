package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 17:52:46
 */
@Data
@TableName("zg_work_schedule")
public class ZgWorkScheduleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 工作计划主表id
	 */
	private String planId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 工作进度
	 */
	private String workContent;
	/**
	 * 领导批示意见
	 */
	private String leaderIdea;
	/**
	 * 1-工作进度 2-领导批示意见
	 */
	private Integer status;
	/**
	 * 批示领导姓名
	 */
	private String leaderName;
	/**
	 * 登录人id
	 */
	private Long userId;
	@Override
	public String toString() {
		return "ZgWorkScheduleEntity [id=" + id + ", planId=" + planId + ", createTime=" + createTime + ", workContent="
				+ workContent + ", leaderIdea=" + leaderIdea + ", status=" + status + ", leaderName=" + leaderName
				+ ", userId=" + userId + "]";
	}
	
	

}
