package com.orhonit.ole.sys.model;

import com.orhonit.ole.common.model.BaseEntity;

import lombok.*;

@Getter
@Setter
public class Dept extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5650370677182531750L;

	private String code;// 主体编号
	private String name_spell;// 名称全拼音
	private String address;// 主体地址
	private String legal_person;// 法定代表人
	private int level;// 主体级别
	private int dept_property;// 主体性质
	private String parent_id;// 上级主体
	private int sort;// 排序
	private String law_type;// 执法性质
	private String tel;// 电话
	private String area_id;// 地区ID
	private String lawarea;// 执法区域ID
	private String if_effect;// 是否启用
	private String del_flag;// 删除标记
	private String create_by;// 创建者用户名
	private String create_name;// 创建者名称
	private String update_by;// 更新人登录名
	private String update_name;// 更新人姓名
	private String name;// 主体名称
	private String mgl_name;// 主体名称（蒙文）
	private String mgl_address;// 地址(蒙文)
	private String mgl_legal_person;// 法定代表人(蒙文)
	private String mgl_create_name;// 创建者名称(蒙文)
	private String mgl_update_name;// 更新人姓名(蒙文)

}
