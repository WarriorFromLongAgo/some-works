package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author xiaobai
 * @date 2019-05-13 14:37:49
 */
@Data
@TableName("core_opinion")
public class CoreOpinionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String opinionId;
	/**
	 * 我的意见
	 */
	private String opinion;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private String creatTime;
	/**
	 * 收件人id
	 */
	private Long addId;
	/**
	 * 收件人
	 */
	private String addName;

}
