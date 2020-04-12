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
@Table(name="ole_ef_check")
@AllArgsConstructor
@NoArgsConstructor
public class LssuedEntity {
	
	@Id
	private String id;
	
	private String checkTitle;		//检查标题
	
	private String checkObject;		//检查对象
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;			//状态
			
	private String checkBasis;		//检查依据
	
	private String checkContent;
	
	private String checkWay;
	
	private String checkNum;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
	
	private String mglCheckTitle;
	
	private String mglCheckObject;
	
	private String mglCheckBasis;
	
	private String mglCheckContent;
	
	private String mglCreateName;
	@Transient
	private String deptId;				//执法部门ID
	@Transient
	private String deptName;
	@Transient
	private String personId;			//执法人员ID
	@Transient
	private String personName;
	@Transient
	private String assistPersonId;			//副执法人员ID
	@Transient
	private String assistPersonName;
	@Transient
	private String comment;
	@Transient
	private String userDeptId;
	@Transient
	private String caseStatus;
	@Transient
	private String roadName;
	
	private String isRelate;
	
	private String caseAccept;
	
}
