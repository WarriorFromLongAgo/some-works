package com.orhonit.modules.enterprise.model;

/**
 * 文件model
 * @author 	cyf
 * @date	2018/11/05 下午8:26:07
 */
public class NopublicFileModel {
	
	private Long id;
	private String fileName;
	private String downPath;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDownPath() {
		return downPath;
	}
	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}
	public NopublicFileModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NopublicFileModel(Long id,String fileName, String downPath) {
		super();
		this.fileName = fileName;
		this.downPath = downPath;
		this.id=id;
	}
	
	

}
