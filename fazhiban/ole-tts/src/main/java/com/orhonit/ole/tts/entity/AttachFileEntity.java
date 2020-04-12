package com.orhonit.ole.tts.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_ef_attach_file")
@AllArgsConstructor
@NoArgsConstructor
public class AttachFileEntity {

	@Id
	private String id;			//主键
	
	private String fileName;	//原文件名
	
	private String fileNewname;	//新文件名
	
	private String filePath;	//文件路径
	
	private String fileTitle;	//文件标题
	
	private String caseId;		//案件ID
	
	private String caseNum;		//案件编号
	
	private String caseStatus; // 案件状态	
	
	private String fileType;	//文件类型
	
	private String pdfUrl;		//PDF文件路径
	
	private String createName;	//创建人名称
	
	private String 	createBy;	//创建人登录帐号
	
	private Date createDate;	//创建日期

}
