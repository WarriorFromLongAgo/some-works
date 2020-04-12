package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_ef_doc_template")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocTemplateEntity {
	
	@Id
	private String id;
	
	private String name;
	
	private String content;
	
	private Integer isEffect;
	
	private String createName;
	
	private String 	createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
	
	private String code;
	
}
