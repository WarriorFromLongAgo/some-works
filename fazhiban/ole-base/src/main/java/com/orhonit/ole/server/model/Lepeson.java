package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lepeson extends BaseEntity<String> {
	private static final long serialVersionUID = -7809315432127036583L;
	private LtcAtt ltcAtt;
	private String code;
	private String name;
	private String sex;
	private String nation;
	private String tel;
	private String political;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private String edu;
	private String card_num;
	private String picture;
	private String duty;
	private String dept_id;
	private String cert_num;
	private String lawarea;
	private String cert_type;
	private String cert_category;
	private String cert_auth;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date cert_time;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date cert_term;
	private String law_type;
	private String if_effect;
	private String del_flag;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date other_date;
	private String create_by;
	private String create_name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String update_by;
	private String update_name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	private String mgl_name;
	private String mgl_create_name;
	private String mgl_update_name;
	private String mgl_dept;
	private String mgl_duty;
}
