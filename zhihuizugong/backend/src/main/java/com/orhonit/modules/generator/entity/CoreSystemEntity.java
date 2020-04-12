package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 党建制度表
 * 
 * @author xiaobai
 * @date 2019-05-18 11:14:27
 */
@Data
@TableName("core_system")
public class CoreSystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private int id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 党建制度标题
	 */
	private String title;
	/**
	 * 党建制度内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 党建制度标题（1：党支部工作职责 2：党员的权利 3：党员的义务 4：三会一课制度 5：党内民主生活会制度 6：党员学习制度）
	 */
	private int titleType;

}
