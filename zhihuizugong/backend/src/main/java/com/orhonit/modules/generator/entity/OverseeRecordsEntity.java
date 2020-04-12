package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 领导督办记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-09 11:14:32
 */
@Data
@TableName("tb_oversee_records")
public class OverseeRecordsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 批示和进度和点评表
	 */
	@TableId
	private Integer recordId;
	/**
	 * 督办id
	 */
	private String overseeId;
	/**
	 * 创建人

	 */
	private Long createBy;
	/**
	 * 记录内容
	 */
	private String recordsContent;
	/**
	 * 创建人所在支部
	 */
	private Integer createDeptId;
	/**
	 * 创建时间
	 */
	private Date crtTime;
	/**
	 * 创建用户所在科室
	 */
	private Integer lowerId;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 记录类别 1 领导批示 2 完成进度 3 领导点评
	 */
	private Integer recordType;
	/**
	 * 工作难度
	 */
	private Integer workHarder;
	/**
	 * 工作效率
	 */
	private Integer workEfficiency;
	/**
	 * 工作成效
	 */
	private Integer workPerformance;

}
