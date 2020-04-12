package com.orhonit.ole.warn.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_ef_doc_content")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocContentEntity {
	
	@Id
	private String id;
	
	private String caseId;
	
	private String templateId;
	
	private String value;
	
	private String partyId;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
}
