package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 党组织表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:01:54
 */
@Data
@TableName("core_organization")
public class CoreOrganizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private String organizationId;
	/**
	 * 组织名称
	 */
	private String organizationName;
	/**
	 * 类型（1：党组织成员 2：历史前沿）
	 */
	private String type;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private String createTime;

}
