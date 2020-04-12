package com.orhonit.modules.generator.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 悟一悟
 * 笔记记录
 * @author 
 *
 */
@TableName("wyw_note")
@Data
public class NoteEntity {
	
	private Integer noteId;    
	
	private String content;    //内容
	 
	private Date createTime;   //创建时间
	
	private String noteTitle;  //标题
	
	
	

}
