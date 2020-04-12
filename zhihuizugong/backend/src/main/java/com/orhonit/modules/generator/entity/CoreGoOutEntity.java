package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 外出登记表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-05-10 13:48:31
 */
@Data
@TableName("core_go_out")
public class CoreGoOutEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 外出登记id
	 */
	@TableId
	private String id;
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
	private Integer whether;
	/**
	 * 外出时间
	 */
	private String goOutTime;
	/**
	 * 返回时间
	 */
	private String returnTime;
	/**
	 * 事由
	 */
	private String leaveThing;

}
