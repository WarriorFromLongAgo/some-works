package com.orhon.smartcampus.modules.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学生学籍基本信息表
 * </p>
 *
 * @author Orhon
 */
@TableName("student_learninfo")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Learninfo extends BaseModel {

	private static final long serialVersionUID=1L;

	@TableId
	private Integer id;  //"学生学籍基本信息id"   
	private Integer user_id;  //"用户id"   
	private Integer student_id;  //"学生基本信息id"   
	private Integer school_id;  //"学校id"   
	private Integer arrive_id;  //"对应届级"   
	private Integer at_school;  //"在校状态(1、在校；0不在校)"   
	private String transaction_type;  //"异动类型（数据字典:transaction_type）"   
	private String membership_number;  //"学籍辅号"   
	private String class_code;  //"班内学号"   
	private String start_year;  //"入学年份"
	private String finished_year;  //"结业年份"
	private String admission_method;  //"招生入学方式(数据字典: admission_methods)"   
	private Integer way_to_study;  //"就读方式(0走读1寄宿）"   
	private String way_to_annex;  //"就读方式附件"   
	private String student_from;  //"学生来源(数据字典:student_from)"   
	private Integer confirmed;  //"是否确认"   
	private String confirmed_by;  //"确认人"   
	private Date created_at;  //"创建时间"
	private Date updated_at;  //"修改时间"
	private Date deleted_at;  //"删除标识"


}
