package com.orhonit.modules.enterprise.entity;

import java.sql.Timestamp;

/**
 * 文件
 * @author 	cyf
 * @date	2018/11/05 下午8:17:11
 */
public class NopublicFile {
	
	
	private Long id;
	private String filePath;
	private String fileType;
	private String fileNike;
	private String fileName;
	private String suffix;
	private Timestamp createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileNike() {
		return fileNike;
	}
	public void setFileNike(String fileNike) {
		this.fileNike = fileNike;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public NopublicFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NopublicFile( String filePath, String fileType, String fileNike, String fileName, String suffix,
			Timestamp createTime) {
		this.filePath = filePath;
		this.fileType = fileType;
		this.fileNike = fileNike;
		this.fileName = fileName;
		this.suffix = suffix;
		this.createTime = createTime;
	}
	
	

}
