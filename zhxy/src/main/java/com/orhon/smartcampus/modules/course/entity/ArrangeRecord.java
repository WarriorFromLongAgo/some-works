package com.orhon.smartcampus.modules.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 排课记录表
 * </p>
 *
 * @author Orhon
 */
@TableName("course_arrange_record")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ArrangeRecord extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;
private Integer course_arrange_id;  //"排课表"   
private Integer teacher_id;  //"教师id"   
private Integer course_id;  //"课程id"   
private Integer room_id;  //"房间id"   
private Integer start_cycle;  //"开始周"   
private Integer end_cycle;  //"结束周"   
private Integer school_id;  //"学校id"   
private LocalDateTime deleted_at; 
}
