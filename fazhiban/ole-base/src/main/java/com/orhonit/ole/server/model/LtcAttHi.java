package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LtcAttHi extends BaseEntity<String> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String dept_id;
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
	private String is_ps;
	private String del_flag;
	private String create_by;
	private String create_name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String update_by;
	private String update_name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	private String name;
	private String mgl_name;
	private String mgl_address;
	private String mgl_legal_person;
	private String mgl_create_name;
	private String mgl_update_name;
	private String short_name;
	private String mgl_short_name;
	private String is_deal;
	private String mgl_lawarea;
}
