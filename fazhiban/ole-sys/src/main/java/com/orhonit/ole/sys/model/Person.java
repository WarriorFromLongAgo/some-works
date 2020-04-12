package com.orhonit.ole.sys.model;

import com.orhonit.ole.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5972056629839752228L;
	
	private String code;//人员编号
	private String name;//人员姓名
	private String sex;//性别
	private String nation;//民族
	private String tel;//电话
	private String political;//政治面貌
	private String birthday;//出生日期
	private String edu;//文化程度
	private String card_num;//身份证号码
	private String picture;//照片
	private String duty;//职务类型
	private String dept_id;//执法主体
	private String cert_num;//执法证号
	private String lawarea;//执法范围
	private String cert_type;//执法证件类型
	private String cert_auth;//执法发证机关
	private String cert_time;//发证时间
	private String cert_term;//证件有效日期
	private String law_type;//执法类型
	private String if_effect;//是否有效
	private String del_flag;//删除标志
	private String create_by;//创建人登录名称
	private String create_name;//创建人名称
	private String update_by;//更新人登录名称
	private String update_name;//更新人名称
	private String mgl_name;//人员姓名(蒙文)
	private String mgl_create_name;//创建人名称(蒙文)
	private String mgl_update_name;//更新人名称(蒙文)

}
