package com.orhonit.ole.sys.model;

import java.util.Date;

import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawAtt extends BaseEntity<Long> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String code;
	private String name_spell;
	private String address;
	private String legal_person;
	private int level;
	private int dept_property;
	private String parent_id;
	private int sort;
	private String law_type;
	private String tel;
	private String area_id;
	private String lawarea;
	private String if_effect;
	private String del_flag;
	private String create_by;
	private String create_name;
	private Date create_date;
	private String update_by;
	private String update_name;
	private Date update_date;
	private String name;
	private String mgl_name;
	private String mgl_address;
	private String mgl_legal_person;
	private String mgl_create_name;
	private String mgl_update_name;

}
