package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 调度参会人
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Data
@TableName("ej_scheduling_people")
public class EjSchedulingPeopleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 调度主表ID
	 */
	private String schedulingId;
	/**
	 * 参会人ID
	 */
	private Long userId;
	/**
	 * 参会人员名称
	 */
	private String userName;
	/**
	 * 参会人员身份证号
	 */
	private String userIdCard;
	/**
	 * 参会人员头像
	 */
	private String headPortrait;
	/**
	 *
	 * 人员任务列表
	 */
	@TableField(exist = false)
	private List<ZgTaskEntity> taskList;

}
