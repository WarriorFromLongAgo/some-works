package com.orhon.smartcampus.modules.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学期表
 * </p>
 *
 * @author bao
 */
@TableName("baseinfo_semester")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Semester extends BaseModel {

	private static final long serialVersionUID=1L;

	@TableId
	private Integer id;  //"学期ID"   
	@NotBlank(message="学期编号不能为空")
	private String semester_code;  //"学期编号" 
	@NotNull(message="学校ID不能为空")
	private Integer school_id;  //"学校ID"   
	private Integer schoolyear_id;  //"学年ID"   
	private String start_at;  //"学期开始时间"
	private String end_at;  //"学期结束时间"
	private Integer num;  //"学期数"   
	private String teaching_start_at;  //"教学开始时间"
	private String teaching_end_at;  //"教学截止时间"
	private Integer week_quantity;  //"总教学周数"   
	private Integer semester_status;  //"学期状态"   
	private String first_week_monday;  //"第一周周一日期"
	private String first_week_sunday;  //"第一周周日日期"
	private LocalDateTime created_at;  //"创建时间"   
	private Integer created_by;  //"创建人"   
	private LocalDateTime updated_at;  //"修改时间"   
	private Integer updated_by;  //"修改人"   
	private LocalDateTime deleted_at;  //"删除标识"   
	private String mark;    
	@JSONField(jsonDirect=true)
	private String semester_name; 






}
