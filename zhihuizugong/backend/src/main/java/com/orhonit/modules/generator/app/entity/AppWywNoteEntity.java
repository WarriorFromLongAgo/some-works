package com.orhonit.modules.generator.app.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * APP端捂一捂
 * @author 
 *
 */
//@Data
@TableName("wyw_note")
public class AppWywNoteEntity implements Serializable {
    
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
	 * 文件名称
	 */
	//private String fileName;
	
	/**
	 * 存储路径
	 */
	private String storagePath;
	
	/**
	 * 创建人
	 */
	private Integer createUserid;
	
	/**
	 * 文件类型    1:图片  2:视频   3:其他
	 */
	private Integer isFiletype;
	
	private Integer lowerId;   //科室ID
	
	private String lowerName;   //科室名称
	
	private String isDel; //逻辑删  0-未删  1-已删
	
	/**
	 * 修改时间
	 */
	//@JsonFormat( pattern="yyyy-MM-dd") 
	private Date updateTime;
	
	
	//** 用户姓名
    private String userTrueName;


	public Integer getNoteId() {
		return noteId;
	}


	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getNoteTitle() {
		return noteTitle;
	}


	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}


	public String getStoragePath() {
		return storagePath;
	}


	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}


	public Integer getCreateUserid() {
		return createUserid;
	}


	public void setCreateUserid(Integer createUserid) {
		this.createUserid = createUserid;
	}


	public Integer getIsFiletype() {
		return isFiletype;
	}


	public void setIsFiletype(Integer isFiletype) {
		this.isFiletype = isFiletype;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
   

	
	  public String getUserTrueName() { return userTrueName; }


	public Integer getLowerId() {
		return lowerId;
	}


	public void setLowerId(Integer lowerId) {
		this.lowerId = lowerId;
	}


	public String getLowerName() {
		return lowerName;
	}


	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}


	public String getIsDel() {
		return isDel;
	}


	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	  
	  
	 
}
