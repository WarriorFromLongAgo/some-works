package com.orhonit.ole.tts.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_warn_person")
@AllArgsConstructor
@NoArgsConstructor
public class WarnPersonEntity {

	@Id
	private String id;
	
	private String personId;
	
	private String deptId;
	
	private String warnId;
	
	private String dealResult;
	
	private String  isMajor;
	
	private String  isDeal;
    
	private Date createDate;
	
	private String createName;
	
	private String createBy;
	
	private Date updateDate;
	
	private String updateName;
	
	private String updateBy;
	
	@Transient
	private String recordTitle;
	
	@Transient
	private String recordStatus;
	
	@Transient
	private String flowType;
	
	@Transient
	private String recordId;
}
