package com.orhonit.ole.tts.dto;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class LssuedDTO {

	private String id;
	
	private String checkTitle;		//检查标题
	
	private String checkObject;		//检查对象
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;			//状态
			
	private String checkBasis;		//检查依据
	
	@Transient
	private String checkContent;
	
	private String checkWay;
	
	private String checkNum;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String mglcheckTitle;
	
	private String mglcheckObject;
	
	private String mglcheckBasis;
	
	@Transient
	private String mglcheckContent;
	
	private String mglcreateName;
	
	private String deptId;				//执法部门ID
	
	private String deptName;
	
	private String personId;			//执法人员ID
	
	private String personName;
	@Transient
	private String comment;
	
	private String isRelate;
	
	private String caseAccept;
	

}
