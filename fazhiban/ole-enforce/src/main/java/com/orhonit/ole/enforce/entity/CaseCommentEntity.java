package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_ef_case_comment")
@AllArgsConstructor
@NoArgsConstructor
public class CaseCommentEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String caseId;

	private String comment;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String mglComment;
	
	private String mglCreateName;
	
	
}
