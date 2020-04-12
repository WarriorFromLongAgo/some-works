package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 每季评比表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-25 15:43:36
 */
@Data
@TableName("core_appraisal")
public class CoreAppraisalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String appraisalId;
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
	private String creatTime;
	/**
	 * 类型（1：廉政会议 2：年度“清风干部” 3：旗级“清风干部”）
	 */
	private Integer appraisalType;

}
