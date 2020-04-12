package com.orhonit.ole.enforce.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_base_dept_person")
@AllArgsConstructor
@NoArgsConstructor
public class DeptPersonEntity {
	@Id
	private String id;
	
	private String code;
	
	private String certNum;
	
	private String deptId;
	
	private String name;
	
}
