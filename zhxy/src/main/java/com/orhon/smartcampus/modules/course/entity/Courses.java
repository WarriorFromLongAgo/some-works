package com.orhon.smartcampus.modules.course.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 课程的数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("course_courses")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Courses extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;    
private String course_slug;  //"课程别名"   
private Integer subject_id;  //"学科id"   
private Integer school_id;  //"学校id"   
private Integer course_code;  //"课程编号"   
private Integer course_kind_dict;  //"课程分类"   
private Integer level_id;  //"课程级别"   
private String type;  //"课程类型（administrative：行政，walking：走班）"   
private LocalDateTime created_at;    
private LocalDateTime updated_at;    
private LocalDateTime deleted_at; 
@JSONField(jsonDirect=true)
private String course_name;
}
