package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 悟一悟  笔记记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 10:03:13
 */
@Data
@TableName("wyw_note")
public class WywNoteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 笔记id
	 */
	@TableId
	private Integer noteId;
	/**
	 * 创建时间
	 */
	@JsonFormat( pattern="yyyy-MM-dd") 
	private Date createTime;
	/**
	 * 发表内容
	 */
	private String content;
	
	/**
	 * 标题
	 */
	private String noteTitle;
	
	/**
	 * 逻辑删  0-未删  1-已删
	 */
	private String isDel; 
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	

}
