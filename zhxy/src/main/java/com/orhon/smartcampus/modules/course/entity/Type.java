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
 * 课程级别表
 * </p>
 *
 * @author Orhon
 */
@TableName("course_type")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Type extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;    
private Integer course_level_order;    
private Integer school_id;  //"学校id"   
private Integer created_by;    
private LocalDateTime created_at;    
private LocalDateTime updated_at;    
private LocalDateTime deleted_at;
@JSONField(jsonDirect=true)
private String name; 
}
