package com.orhon.smartcampus.modules.base.entity;

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
 * 年级
 * </p>
 *
 * @author Orhon
 */
@TableName("base_grades")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Grades extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"年级id"   
    private String grade_slug;  //"年级别名"   
    private Integer period_id;  //"学段id"   
    private String grade_number;  //"年级序号"   
    private Integer grade_order;  //"年级顺序"   
    private LocalDateTime created_at;    
    private LocalDateTime updated_at;    
    private LocalDateTime deleted_at;    
    private String mark;    
    @JSONField(jsonDirect=true)
    private String grade_name; 
}
