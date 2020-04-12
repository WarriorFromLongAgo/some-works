package com.orhonit.ole.warn.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_ef_party_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyInfoEntity {
	
	@Id
	private String id;
	
	private String name;
	
	private String postCode;
	
	private String idCard;
	
	private Integer sex;
	
	private Integer age;
	
	private String tel;
	
	private String address;
	
	private String unitName;
	
	private String unitAddress;
	
	private String legalName;
	
	private String orgIdCard;
	
	private String orgCode;
	
	private Integer type;
	
	private String caseId;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
	
	private String mglName;
	
	private String mglAddress;
	
	private String mglUnitName;
	
	private String mglLegalName;
	
	private String mglCreateName;
	
	private String mglUpdateName;
}
