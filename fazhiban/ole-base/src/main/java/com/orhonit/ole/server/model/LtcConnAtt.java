package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LtcConnAtt extends BaseEntity<Long> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String potence_id;
	private String potence_name;
	private String dept_id;
	private String dept_id_name;
	private String dept_id_agent;
	private String dept_id_agent_name;
	private String is_effect;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date effect_date;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date invalid_date;
	private String del_flag;
	private String create_name;
	private String create_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String update_name;
	private String update_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	private String mgl_create_name;
	private String mgl_update_name;
}
