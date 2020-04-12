package com.orhon.smartcampus.modules.systemctl.entity;

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
 *
 * </p>
 *
 * @author Orhon
 */
@TableName("systemctl_operations")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Operations extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"ID" 
    @JSONField(jsonDirect =  true )
    private String title;  
    @JSONField(jsonDirect =  true )
    private String apis;  //"路由地址"   
    private String menus;    
    @JSONField(jsonDirect =  true )
    private String widgets;    
    private Integer module_id;  //"权限所属模块"
    private Date created_at;  //"创建时间"
    private Date updated_at;  //"更新时间"
    private Date deleted_at;  //"删除时间"
}
