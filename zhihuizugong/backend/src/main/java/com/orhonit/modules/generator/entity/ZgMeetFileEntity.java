package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
@Data
@TableName("zg_meet_file")
public class ZgMeetFileEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	/**
	 * 会议id
	 */
	private String meetId;
	/**
	 * 附件名称
	 */
	private String fileName;
	/**
	 * 新附件名称
	 */
	private String fileNewName;
	/**
	 * 附件地址
	 */
	private String filePath;
	/**
	 * 附件后缀名
	 */
	private String fileSuffix;
	/**
	 * 1-会议资料 2-会议记录 3-会议总结
	 */
	private Integer fileType;
	/**
	 * 创建时间
	 */
	private Date createTime;
}
