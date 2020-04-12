package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_warn_complain")
@AllArgsConstructor
@NoArgsConstructor
public class WarnComplainEntity {

	@Id
	private int id;
	
	private String name;
	
	private String tel;
	
	private String email;
	
	private String address;
	
	private String content;
	
	private String lang;
	
	private Date createDate;
	
	private String createBy;
	
	private String createName;
	
	private Date updateDate;
	
	private String updateName;
	
	private String updateBy;
}
