package com.orhonit.ole.sys.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_ef_review_record")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRecordEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String caseId;

	private Integer batch;
	
	private String  status;
	
	private Integer score;
	
	private String remark;
	
	private String createName;
	
	private String 	createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private String oneComment;
	
	private String twoComment;
	
	private Date updateDate;
	
	@Transient
	private String statusName;
	
	@Transient
	private String caseName;
	
	@Transient
	private String caseNum;
}
