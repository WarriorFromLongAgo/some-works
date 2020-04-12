package com.orhonit.modules.generator.entity;



import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 承诺践诺/工作任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:05:31
 */
@Data
@TableName("zg_task")
public class ZgTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 任务名称
	 */
	private String workTask;
	/**
	 * 参与任务用户id
	 */
	private Long userId;
    /**
     * 创建用户id
     */
    private Long createUser;
	/**
	 * 1-党员 2-干部 3 调度 4 督办任务
	 */
	private String taskType;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	/**
	 * 完成时限
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date completionTime;
	/**
	 * 参会人员中间表ID
	 */
	private Integer peopleId;
	/**
	 * 认领时间
	 */
	private Date claimTime;
	/**
	 * 状态 1 未认领 2 进行中 3 已完成 4 未完成
	 */
	private Integer status;
	/**
	 * 参会人员名称
	 */
	private String userName;
	/**
	 * 参会人员身份证号
	 */
	private String userIdCard;
	/**
	 * 是否已读 1 是 2 否(任务通知使用字段)
	 */
	private Integer isRead;
	/**
	 * 最新的进度
	 */
	@TableField(exist=false)
	private Integer schedule;
	/**
	 * 完成情况内容
	 */
	@TableField(exist=false)
	private String completion;
	/**
	 * 完成情况时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@TableField(exist=false)
	private Date completionDate;
	/**
	 * 督办情况内容
	 */
	@TableField(exist=false)
	private String rigorous;
	/**
	 * 督办情况时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@TableField(exist=false)
	private Date rigorousDate;
	/**
	 * 任务总数
	 */
	@TableField(exist=false)
	private Integer taskCount;
	/**
	 * 未完成任务数
	 */
	@TableField(exist=false)
	private Integer unfinished;
	/**
	 * 已完成任务数
	 */
	@TableField(exist=false)
	private Integer complete;
	/**
	 * 饼图统计-value
	 */
	@TableField(exist=false)
	private Integer value;
	/**
	 * 饼图统计-name
	 */
	@TableField(exist=false)
	private String name;
	/**
	 * 工作任务的完成情况
	 */
	@TableField(exist=false)
	private List<ZgTaskFinishEntity> completionList;
	/**
	 * 工作任务的督办情况
	 */
	@TableField(exist=false)
	private List<ZgTaskFinishEntity> rigorousList;
	/**
	 * 工作任务的完成/督办情况
	 */
	@TableField(exist=false)
	private List<ZgTaskFinishEntity> finishList;
	/**
	 * 调度任务参与人员
	 */
	@TableField(exist=false)
	private List<Long> joinPeopleList;
	/**
	 * 调度任务主持人
	 */
	@TableField(exist=false)
	private Long schedulingCompere;
	/**
	 * 调度任务创建人
	 */
	@TableField(exist=false)
	private Long schedulingCreateUser;


}
