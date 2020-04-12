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
@Table(name="ole_ef_case_deal")
@AllArgsConstructor
@NoArgsConstructor
public class CaseDealEntity {
	@Id
	private String id;
	
	private String caseId;
	
	private String caseNum;
	
	private String dealMode;
	
	private Integer dealType;
	
	private String dealContent;
	
	private Integer caseStatus;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String mglDealContent;
	
	private String mglCreateName;
	
	private Integer isDeal;
}
