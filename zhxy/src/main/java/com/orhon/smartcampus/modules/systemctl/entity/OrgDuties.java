package com.orhon.smartcampus.modules.systemctl.entity;

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
 *
 * </p>
 *
 * @author Orhon
 */
@TableName("systemctl_org_duties")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrgDuties extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"模块ID"
    @JSONField(jsonDirect =  true )   
    private String duties_name;  //"职务名称"   
    private Integer duties_order;  //"职务排序"   
    private Integer school_id;  //"学校id"   
    private Integer parent_id;  //"父级职务"   
    private Integer level;  //"职务等级"   
    private LocalDateTime created_at;    
    private LocalDateTime updated_at;    
    private LocalDateTime deleted_at; 
}
