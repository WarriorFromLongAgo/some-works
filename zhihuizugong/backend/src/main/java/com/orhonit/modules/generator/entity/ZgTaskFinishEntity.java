package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工作任务副表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:28:24
 */
@Data
@TableName("zg_task_finish")
public class ZgTaskFinishEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 任务id
	 */
	private Integer taskId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 1-完成情况 2-督办内容
	 */
	private String contentType;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建用户名称
	 */
	private String createName;
	/**
	 * 创建用户头像
	 */
	private String headPortrait;
	/**
	 * 完成进度
	 */
	private Integer schedule;
	/**
	 * 完成情况/督办情况附件列表
	 */
	@TableField(exist = false)
	private List<EjSchedulingFileEntity> fileList;
	
}
