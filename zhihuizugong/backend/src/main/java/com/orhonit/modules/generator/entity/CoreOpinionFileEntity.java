package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 意见附件
 * 
 * @author xiaobai
 * @date 2019-05-13 14:37:50
 */
@Data
@TableName("core_opinion_file")
public class CoreOpinionFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private int id;
	/**
	 * 附件名称
	 */
	private String fileName;
	/**
	 * 新附件名称
	 */
	private String newFileName;
	/**
	 * 附件地址
	 */
	private String filePath;
	/**
	 * 附件类型 1 图片 2 视频 3 文档
	 */
	private Integer fileType;
	/**
	 * 文件类型
	 */
	private String fileSuffix;
	/**
	 * 意见ID
	 */
	private String opinionId;

}
