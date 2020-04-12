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
@Table(name="ole_base_area")
@AllArgsConstructor
@NoArgsConstructor
public class BaseAreaEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String code;
	
	private String level;
	
	private String name;
	
	private String parentId;
	
	private String nameEn;
	
	private String sort;
	
	private Double area;
	
	private String mglName;
	
}