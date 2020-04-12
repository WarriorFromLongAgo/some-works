package com.orhonit.ole.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LarConAtt extends BaseEntity<Long> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String right_duty_id;
	private String wz_catalog_id;
	private String wz_catalog_name;
	private String wz_content;
	private String wz_item;
	private String fz_catalog_id;
	private String fz_catalog_name;
	private String fz_content;
	private String fz_item;
	private String is_effect;
	private String del_flag;
	private String lang;
	private String create_name;
	private String create_by;
	private String update_name;
	private String update_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date update_date;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String mgl_create_name;
	private String mgl_update_name;
	

}
