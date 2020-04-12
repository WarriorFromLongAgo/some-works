package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 财务管理人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:02
 */
@Data
@TableName("tb_finance_people")
public class FinancePeopleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 人员名称
	 */
	private String peopleName;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 科室ID
	 */
	private Integer lowerId;
	/**
	 * 科室名称
	 */
	private String lowerName;
	/**
	 * 部门ID
	 */
	private Integer userDept;
	/**
	 * 财务管理主表ID
	 */
	private String financeId;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
