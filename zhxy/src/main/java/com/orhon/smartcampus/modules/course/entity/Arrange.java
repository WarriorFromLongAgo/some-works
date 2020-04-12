package com.orhon.smartcampus.modules.course.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 排课
 * </p>
 *
 * @author Orhon
 */
@TableName("course_arrange")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Arrange extends BaseModel {

private static final long serialVersionUID=1L;
@TableId
private Integer id;  //"排课表id"   
private Integer semester_id;  //"学期id"   
private Integer class_type;  //"第几节课"   
private Integer week_id;  //"周几"   
private Integer buildings_id;  //"楼宇id"   
private Integer unit_id;  //"单元id"   
private Integer floor_id;  //"楼层id"   
private Integer room_id;  //"房间id"   
private Integer eclass_id;  //"班级id"   
private Integer course_id;  //"课程id"   
private Integer user_id;  //"教师id"   
private Integer start_cycle;  //"开始周期"   
private Integer end_cycle;  //"结束周期"   
private Integer created_by;  //"创建人"   
private LocalDateTime created_at;  //"创建时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer school_id;  //"学校id"   
private LocalDateTime deleted_at; 
}
