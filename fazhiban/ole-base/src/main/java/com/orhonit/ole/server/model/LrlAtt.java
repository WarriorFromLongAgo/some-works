package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LrlAtt extends BaseEntity<Long> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String code;
	private String name;
	private String name_en;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	private String effect;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date publish_date;
	private int version;
	private String effect_level;
	private String effect_level_id;
	private String pub_dept;
	private String item_type;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date act_date;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date effect_date;
	private int use_times;
	private String source_href;
	private String is_effect;
	private String del_flag;
	private String create_name;
	private String create_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String update_name;
	private String update_by;
	private String mgl_name;
	private String mgl_create_name;
	private String mgl_update_name;

}
