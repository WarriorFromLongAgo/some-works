package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 调度附件表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Data
@TableName("ej_scheduling_file")
public class EjSchedulingFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 附件名称
	 */
	private String fileName;
	/**
	 * 附件类别
	 */
	private Integer fileType;
	/**
	 * 附件后缀
	 */
	private String fileSuffix;
	/**
	 * 附件路劲
	 */
	private String filePath;
	/**
	 * 调度主表ID
	 */
	private String schedulingId;
	/**
	 * 附件新名称
	 */
	private String fileNewName;
	/**
	 * 记录表ID(完成情况/督办情况)
	 */
	private Integer finishId;
	/**
	 * 类型 1 调度附件 2 完成情况/督办情况附件
	 */
	private Integer type;

}
