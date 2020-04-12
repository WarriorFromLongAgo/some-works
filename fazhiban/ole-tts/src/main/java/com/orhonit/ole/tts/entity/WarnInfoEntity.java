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
@Table(name="ole_warn_info")
@AllArgsConstructor
@NoArgsConstructor
public class WarnInfoEntity {

	@Id
	private String id;
	
	private Integer level;
	
	private String content;
	
	private String recordId;
	
	private String recordCode;

	private Integer type;
	
	private String  taskId;
	
	private String warnType;
	
	private Date createDate;
	
	private String createName;
	
	private String createBy;
	
	private Date updateDate;
	
	private String updateName;
	
	private String updateBy;
	
	//new add by liuzh
	private String recordTitle;
	
	private String recordStatus;
	
	private String flowType;
	
	private String star;
}
