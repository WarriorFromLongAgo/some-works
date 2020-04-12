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
 * 学校类型
 * </p>
 *
 * @author Orhon
 */
@TableName("base_schooltypes")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SchoolTypes extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId    
    private Integer id;   
    @JSONField(jsonDirect=true)
    private String name;  //"名称"   
    private String slug;  //"学校类型别名"   
    private Integer orders;  //"顺序"   
    private String mark;  //"标识" 
    @JSONField(jsonDirect=true)
    private String period;  //"学段"   
    private LocalDateTime created_at;    
    private LocalDateTime updated_at;    
    private LocalDateTime deleted_at; 
}
