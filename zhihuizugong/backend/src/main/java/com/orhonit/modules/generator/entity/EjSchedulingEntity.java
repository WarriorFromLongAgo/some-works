package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 调度主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Data
@TableName("ej_scheduling")
public class EjSchedulingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 调度标题
	 */
	private String schedulingTitle;
	/**
	 * 调度内容
	 */
	private String schedulingContent;
	/**
	 * 开始时间
	 */
	private Date satrtTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 主持人
	 */
	private Long compere;
	/**
	 * 主持人名称
	 */
	private String compereName;
	/**
	 * 调度类别 1 视频调度 2 其他调度
	 */
	private Integer schedulingType;
	/**
	 * 创建人
	 */
	private Long createUser;
	/**
	 * 創建时间
	 */
	private Date createTime;
	/**
	 * 任务状态 1 未开始 2 进行中 3 已完成
	 */
	private Integer stats;
	/**
	 * 参会人员中间表列表
	 */
	@TableField(exist = false)
	private List<EjSchedulingPeopleEntity> peopleList;
	/**
	 * 当前登录用户参会人员中间表
	 */
	@TableField(exist = false)
	private EjSchedulingPeopleEntity people;
	/**
	 * 调度附件列表
	 */
	@TableField(exist = false)
	private List<EjSchedulingFileEntity> fileList;

}
