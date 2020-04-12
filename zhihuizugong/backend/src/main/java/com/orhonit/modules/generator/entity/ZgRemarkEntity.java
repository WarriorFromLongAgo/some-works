package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 领导点评
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 11:33:57
 */
@Data
@TableName("zg_remark")
public class ZgRemarkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 工作计划主表id
	 */
	private String planId;
	/**
	 * 点评领导id
	 */
	private Long remarkLeaderId;
	/**
	 * 点评领导姓名
	 */
	private String remarkLeaderName;
	/**
	 * 工作评价
	 */
	private String remarkWork;
	/**
	 * 工作难度
	 */
	private Integer workDifficulty;
	/**
	 * 工作效率
	 */
	private Integer workPiece;
	/**
	 * 工作成效
	 */
	private Integer workResult;
	/**
	 * 完成质量
	 */
	private Integer workQuality;
	/**
	 * 创建时间
	 */
	private Date createTime;


}
