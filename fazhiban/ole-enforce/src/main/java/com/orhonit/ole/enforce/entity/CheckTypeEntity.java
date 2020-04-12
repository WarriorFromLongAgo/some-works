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
@Table(name="ole_ef_check_type")
@AllArgsConstructor
@NoArgsConstructor
public class CheckTypeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private Integer sort;
	
	private String deptId;
	
	private String delFlag;
	
	private String deptName;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;

	private String isEffect;
	
}
