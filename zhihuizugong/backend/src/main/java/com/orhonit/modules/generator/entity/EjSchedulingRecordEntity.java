package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 调度记录表(完成情况/督办情况)
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Data
@TableName("ej_scheduling_record")
public class EjSchedulingRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 调度主表ID
	 */
	private String schedulingId;
	/**
	 * 进度
	 */
	private Integer schedule;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 记录类型 1 完成情况 2 督办内容
	 */
	private Integer type;
	/**
	 * 横坐标
	 */
	private Integer coordinatesX;
	/**
	 * 纵坐标
	 */
	private Integer coordinatesY;
	/**
	 * 调度人员认领任务ID
	 */
	private Integer peopleId;
	/**
	 * 督办领导ID
	 */
	private Long userId;

}
