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
@TableName("systemctl_menus")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Menus extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"模块ID"
    @JSONField(jsonDirect =  true )   
    private String title;  //"菜单标题"   
    private String name;  //"路由标识名"   
    private String path;  //"路由地址"   
    private String component;  //"路由组件"   
    private Integer module_id;  //"模块id"   
    private String category;  //"菜单分类[personal,normal,classwork]"   
    private String type;  //"菜单类型【router,outer_link】"   
    private Integer parent_id;  //"父id"   
    private Integer level;  //"层级"   
    @JSONField(jsonDirect =  true )
    private String meta;  //"扩展信息" 
    @JSONField(jsonDirect =  true )
    private String clients;  //"客户端[pc_mn,pc_cn,app_cn,app_mn]"   
    private String icon;  //"图标"   
    private Integer ordered;  //"顺序"   
    private Integer status;  //"启用状态"   
    private Date created_at;  //"创建时间"
    private Date updated_at;  //"修改时间"
    private Date deleted_at;  //"删除时间"
}
