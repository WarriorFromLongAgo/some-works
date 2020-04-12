package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 党务公开表
 * 
 * @author xiaobai
 * @date 2019-05-18 15:14:02
 */
@Data
@TableName("core_open")
public class CoreOpenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private String openId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 副标题
	 */
	private String subheading;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 类型（1：党组织重大决定 2：党建工作计划 3：总结 4：党费缴纳情况 5：发展党员情况 6：党组织转接）
	 */
	private String openType;

}
