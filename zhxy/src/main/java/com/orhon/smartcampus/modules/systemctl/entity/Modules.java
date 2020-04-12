package com.orhon.smartcampus.modules.systemctl.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;


import lombok.AllArgsConstructor;
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
@TableName("systemctl_modules")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Modules extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"模块ID"
    @JSONField(jsonDirect =  true )
    private String module_name;  //"模块名称"   
    private String category;  //"模块分类"   
    private Integer module_order;  //"排序"   
    private String mark;  //"模块标识"   
    private Integer home_id;  //"模块首页id（关联菜单id）"
    @JSONField(jsonDirect =  true )
    private String clients;  //"支持的客户端[pc_cn,pc_mn,app_cn,app_mn]"   
    private Integer status;  //"模块启用状态（1为有效、0为无效）"   
    private String icon;  //"图标"
    @JSONField(jsonDirect =  true )
    private String meta;  //"模块扩展信息"   
    private Date created_at;  //"创建时间"
    private Date updated_at;  //"更新时间"
    private Date deleted_at;  //"删除时间"
}
