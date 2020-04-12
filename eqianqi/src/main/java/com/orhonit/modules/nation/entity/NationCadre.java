package com.orhonit.modules.nation.entity;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.DataSource;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 民族干部
 * @author cyf
 * @date 2018/11/06 下午5:13:59
 */
@Data
@TableName("nation_cadre")
public class NationCadre {

	@TableId
	private Long id;
	private String name;//姓名
	private String sex;//性别  0女  1男
	private Long nation;//民族
	private String nativePlace;//籍贯
	private String idCard;//身份证
	private Long politicsStatus;//政治面貌
	private Integer phone;//手机号
	private String unit;//服务单位
	private Long duty;//职务
	private Timestamp joinWorkTime;//参加工作时间
	private Timestamp takeOfficeTime;//任职时间
	private String educationalInformation;//教育信息
	private String resume;//个人简历
	private String recognition;//表彰情况
	private Long createUser;//创建用户
	private Timestamp createTime;//创建时间

}
