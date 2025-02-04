package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lesection extends BaseEntity<Long> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String code;
	private String name;
	private String level;
	private String parent_id;
	private String mgl_parent_id;
	private String name_en;
	private String sort;
	private String is_effect;
	private String del_flag;
	private String create_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String update_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	private String create_name;
	private String update_name;
	private String mgl_name;
	private String mgl_create_name;
	private String mgl_update_name;
}
