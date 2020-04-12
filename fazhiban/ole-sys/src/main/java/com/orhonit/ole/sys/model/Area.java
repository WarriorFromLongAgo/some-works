package com.orhonit.ole.sys.model;


import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Area extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4891317270902913091L;

		private  String code;//编码
		private  String name;//名称
		private  String parent_id; //父级地区
		private  String name_en; //英文名
		private  String sort ;//排序
		private  String is_effect;//是否启用
		private  String del_flag; //删除标志
		private  String create_by; //创建人登录名称
		private  String update_by;//更新人登录名称
		private  String create_name; //创建人名称
		private  String update_name;//更新人名称
		private  String mgl_name; //区划名称_蒙文
		private  String mgl_create_name; //创建人名称(蒙文)
		private  String mgl_update_name; //更新人名称(蒙文)
	
}
