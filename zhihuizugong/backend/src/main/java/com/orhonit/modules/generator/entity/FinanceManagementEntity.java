package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 财务管理主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:11:59
 */
@Data
@TableName("tb_finance_management")
public class FinanceManagementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人ID
	 */
	private Long createBy;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 文章类型 1 财务公开 2 公车管理 3 固定资产管理
	 */
	private Integer type;
	/**
	 * 创建人科室
	 */
	private Integer lowerId;
	/**
	 * 创建人部门
	 */
	private Integer deptId;

}
