package com.orhonit.ole.enforce.entity;

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
@Table(name="ole_ef_check_daily")
@AllArgsConstructor
@NoArgsConstructor
public class CheckDailyEntity {
	@Id
	private String id;
	
	private String assistPersonId;
	
	private String personId;
	
	private String checkTitle;
	
	private String checkNum;
	
	private String checkMode;
	
	private String checkModeId;
	
	private String status;
	
	private String checkObjectType;
	
	private String checkedUserName;
	
	private String checkedUserId;
	
	private Date checkedDate;
	
	private Double lng;
	
	private Double lat;
	
	private String roadName;
	
	private String checkSituation;
	
	private String unitName;
	
	private String registNum;
	
	private String legalPerson;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String checkId;
	
	private String mglCheckedUserName;
	
	private String mglRoadName;
	
	private String mglCheckSituation;
	
	private String mglUnitName;
	
	private String mglLegalPerson;
	
	private String mglCreateName;
	
	@Transient
	private String comment;
	
	private String isRelate;
	
	private String caseAccept;
	
	private String deptId;
	
	@Transient
	private String caseStatus;
}
