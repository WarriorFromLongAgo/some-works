package com.orhon.smartcampus.modules.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学段信息
 * </p>
 *
 * @author Orhon
 */
@TableName("base_periods")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Periods extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId    
    private Integer id;  //"学段id"   
    private String period_slug;  //"学段别名	"   
    private String period_number;  //"学段序号"   
    private Integer period_order;  //"学段顺序"
    private Date created_at;    
    private Date updated_at;    
    private Date deleted_at;    
    private String mark;  
    @JSONField(jsonDirect =  true )
    private String period_name; 
}
