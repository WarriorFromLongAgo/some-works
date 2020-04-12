package com.orhonit.ole.tts.entity;

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
	
	private Integer deptProperty;
	
	private String lawType;
	
	private String level;   //级别
	
}
