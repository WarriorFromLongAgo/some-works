package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LarAttHi extends BaseEntity<String> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String potence_id;
	private String code;
	private String name;
	private String parent_id;
	private String process_id;
	private String duty;
	private String duty_ref;
	private String approval_req;
	private String acc_basis;
	private String limit_time;
	private String level;
	private String remarks;
	private String is_effect;
	private String del_flag;
	private String create_name;
	private String create_by;
	private String update_name;
	private String update_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String mgl_remarks;
	private String mgl_duty;
	private String mgl_duty_ref;
	private String mgl_approval_req;
	private String mgl_acc_basis;
	private String mgl_create_name;
	private String mgl_update_name;
	private String pro_type;
	private String is_deal;

}
