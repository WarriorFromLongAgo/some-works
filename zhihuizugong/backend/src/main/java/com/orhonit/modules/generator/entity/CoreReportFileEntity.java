package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 党务公开附件表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
@Data
@TableName("core_report_file")
public class CoreReportFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
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
	 * 每季报告ID
	 */
	private String reportId;

}
