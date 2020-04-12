package com.orhonit.ole.sys.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_base_area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaEntity {

	@Id
	private int id;
	private String level;
	private String code;
	private String name;
	private String parentId;
	private String nameEn;
	private String sort;
	private Double area;
	private String isEffect;
	private String delFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String createName;
	private String updateName;
	private String mglName;
	private String mglCreateName;
	private String mglUpdateName;
}
