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
@Table(name="ole_ef_case")
@AllArgsConstructor
@NoArgsConstructor
public class CaseEntity {
	
	@Id
	private String id;
	
	private String caseName;
	
	private Integer caseStatus;
	
	private String caseSource;
	
	private String caseAddress;
	
	private Double lng;
	
	private Double lat;
	
	private String exeType;
	
	private String dataSource;
	
	private Date caseApplyDate;
	
	private String briefCaseContent;
	
	private String caseHandler;
	
	private String caseZpr;
	
	private Date caseZpdate;
	
	private String caseZzfryid;
	
	private String caseZzfryname;
	
	private String caseFzfryid;
	
	private String caseFzfryname;
	
	private String caseNum;
	
	private Date updateDate;
	
	private String caseType;
	
	private String deptId;
	
	private String caseReason;
	
	private Date caseTime;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private String mglBriefCaseContent;
	
	private String mglCaseFzfryname;
	
	private String mglCaseZzfryname;
	
	private String mglCaseName;
	
	private String mglCreateName;
	
	private String mglUpdateName;
	
	@Transient
	private String comment;
	
	private String limitValue;
	
	private String limitType;
	
	private String potenceId;
	
	private String potenceLawMapId;
	
	private String checkId;
		
	private String isPs;		//新增：是否公示
	
	private String flowType;
}
