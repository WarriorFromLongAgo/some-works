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
@Table(name="ole_ef_check_person")
@AllArgsConstructor
@NoArgsConstructor
public class LssuedPersonEntity {
	
	@Id
	private String id;

	private String check_id;
	
	private String dept_id;
	
	private String person_id;
	
	private String createName;
	
	private String createBy;
	
	private Date createDate;
	
	private String mglcreateName;
}
