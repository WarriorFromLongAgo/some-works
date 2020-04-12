package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 组工之魂 文件表
 * 
 * @author YaoSC
 *
 */
@Data
@TableName("fsoul_file")
public class AppFsoulFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件ID
	 */
	@NotNull
	private String fsoulFileId;
	/**
	 * 模块类型 1:捂一捂 2:大家来分享 3:组工讲堂  4:组工书苑  默认:0
	 */
	@NotNull
	private Integer fsoulModularType;

	/**
	 * 存储路径
	 */
	private String fsoulFileUrl;

	/**
	 * 上传文件名
	 */
	private String fsoulOriginalFileName;

	/**
	 * 现文件名
	 */
	private String newFileName;

	/**
	 * 创建时间
	 */
	private Date fsoulCreateTime;

	/**
	 * 文件类型 1:图片 2:视频 默认:0
	 */
	@NotNull
	private Integer fileType;

}
