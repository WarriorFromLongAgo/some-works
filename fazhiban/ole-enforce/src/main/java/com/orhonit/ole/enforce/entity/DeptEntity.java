package com.orhonit.ole.enforce.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_base_dept")
@AllArgsConstructor
@NoArgsConstructor
public class DeptEntity {

	@Id
	private String id;
	
	private String code;
	
	private String areaId;
	
	private String name;
	
	private String lawType;
	
	private String level;   //级别
	
	private Integer deptProperty;
	
	private Integer parentId;
	
	private String nameSpell;
	
	private String address;
	
	private String legalPerson;
	
	private String tel;
	
	private String shortName;
	
	private String mglShortName;
	
	private String mglName;
	
}
