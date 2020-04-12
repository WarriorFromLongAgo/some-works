package com.orhonit.ole.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LrlDetAtt extends BaseEntity<Long> {

	private static final long serialVersionUID = -7809315432127036583L;
	private String item_code;
	private String law_id;
	private String parent_id;
	private String item_content;
	private String item_name;
	private String item_des;
	private String item_explain;
	private String remarks;
	private String del_flag;
	private String version;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date publish_date;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date effect_date;
	private int use_times;
	private String create_by;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String update_by;
	private Date update_date;
	private String create_name;
	private String update_name;
	private String mgl_item_name;
	private String mgl_item_content;
	private String mgl_item_des;
	private String mgl_item_explain;
	private String mgl_remarks;
	private String mgl_create_name;
	private String mgl_update_name;
	private LrlAtt lrlAtt;

}
