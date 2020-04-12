package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 财务管理附件表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:00
 */
@Data
@TableName("tb_finance_file")
public class FinanceFileEntity implements Serializable {
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
	 * 附件新名称
	 */
	private String fileNewName;
	/**
	 * 附件路劲
	 */
	private String filePath;
	/**
	 * 附件类型 1 表示图片,2 表示文档,3 表示视频,4 表示种子,5 表示音乐,6 表示其它
	 */
	private Integer fileType;
	/**
	 * 附件后缀
	 */
	private String fileSuffix;
	/**
	 * 财务管理主表ID
	 */
	private String financeId;

}
