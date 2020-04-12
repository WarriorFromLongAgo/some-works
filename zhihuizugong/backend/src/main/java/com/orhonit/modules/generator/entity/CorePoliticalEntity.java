package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 生活时时讲
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 16:36:25
 */
@Data
@TableName("core_political")
public class CorePoliticalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
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
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 附件地址
	 */
	private String filePath;
	/**
	 * 类型（1：政治标准 2：政治要求 3：政治纪律 4：政治规矩 5：政治文化）
	 */
	private Integer politicalType;

}
