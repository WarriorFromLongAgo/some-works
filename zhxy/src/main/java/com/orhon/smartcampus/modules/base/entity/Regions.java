package com.orhon.smartcampus.modules.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 地区信息
 * </p>
 *
 * @author Orhon
 */
@TableName("base_regions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Regions extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    private String region_slug;  //"别名"   
    private Integer parent_id;    
    private Integer level;    
    private Integer region_order;  //"顺序"   
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    @JSONField(jsonDirect=true)
    private String region_name; 
}
