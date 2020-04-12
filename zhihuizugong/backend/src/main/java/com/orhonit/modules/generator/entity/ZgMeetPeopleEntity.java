package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

import javax.persistence.Transient;

/**
 * 参会人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 16:28:47
 */
@Data
@TableName("zg_meet_people")
public class ZgMeetPeopleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 会议id
	 */
	private String meetId;
	/**
	 * 参会人id
	 */
	private Long userId;
	/**
	 * 参会人姓名
	 */
	private String userName;
	/**
	 * 是否能看会议记录 0-否 1-是
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 0-未读 1-已读
	 */
	private Integer readType;

}
