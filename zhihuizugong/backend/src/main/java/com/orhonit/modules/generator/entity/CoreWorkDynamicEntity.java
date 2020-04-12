package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作队动态表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 11:29:42
 */
@Data
@TableName("core_work_dynamic")
public class CoreWorkDynamicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工作队-动态id
	 */
	@TableId
	private String dynamicId;
	/**
	 * 工作队id(1：包联工作队 2：志愿服务队 3：爱心乐超市)
	 */
	private Integer workId;
	/**
	 * 工作队主键id
	 */
	private String serveId;
	/**
	 * 动态标题
	 */
	private String title;
	/**
	 * 动态内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建人ID
	 */
	private Long userId;
	/**
	 * 创建人
	 */
	private String userName;
	/**
	 * 附件地址
	 */
	private String filePath;

}
