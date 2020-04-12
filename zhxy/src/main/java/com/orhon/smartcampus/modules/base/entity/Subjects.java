package com.orhon.smartcampus.modules.base.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
 * 学科
 * </p>
 *
 * @author Orhon
 */
@TableName("base_subjects")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Subjects extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"年级id"   
    private String subject_slug;  //"年级别名"
    private String subject_number;  //"学科序号"   
    private Integer subject_order;  //"学科顺序"   
    private String icon;    
    private String type;  //"single"   
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private String mark;  
    @JSONField(jsonDirect=true)
    private String subject_name; 
}
