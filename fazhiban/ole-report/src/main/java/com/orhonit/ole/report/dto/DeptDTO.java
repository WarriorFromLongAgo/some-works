package com.orhonit.ole.report.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptDTO {
    private String code;

    private String name_spell;

    private String id;

    private String address;

    private String legal_person;

    private Integer level;

    private Integer dept_property;

    private String parent_id;

    private Integer sort;

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

	@Override
	public String toString() {
		return "DeptDTO [code=" + code + ", name_spell=" + name_spell + ", id=" + id + ", address=" + address
				+ ", legal_person=" + legal_person + ", level=" + level + ", dept_property=" + dept_property
				+ ", parent_id=" + parent_id + ", sort=" + sort + ", law_type=" + law_type + ", tel=" + tel
				+ ", area_id=" + area_id + ", lawarea=" + lawarea + ", if_effect=" + if_effect + ", del_flag="
				+ del_flag + ", create_by=" + create_by + ", create_name=" + create_name + ", create_date="
				+ create_date + ", update_by=" + update_by + ", update_name=" + update_name + ", update_date="
				+ update_date + ", name=" + name + ", mgl_name=" + mgl_name + ", mgl_address=" + mgl_address
				+ ", mgl_legal_person=" + mgl_legal_person + ", mgl_create_name=" + mgl_create_name
				+ ", mgl_update_name=" + mgl_update_name + "]";
	}

}