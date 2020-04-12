package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 每季报告表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@Data
@TableName("core_report")
public class CoreReportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String reportId;
	/**
	 * 用户id
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
	private String creatTime;
	/**
	 * 类型（1：履职情况报告 2：落实情况报告 3：自律情况报告）
	 */
	private Integer reportType;

}
