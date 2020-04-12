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
@TableName("systemctl_widgets")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Widgets extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"ID"
    @JSONField(jsonDirect =  true )
    private String title;  //"组件标题"   
    private String name;  //"组件标识名"
    @JSONField(jsonDirect =  true )
    private String apis;  //"所用API"   
    private String type;  //"组件类型(modulecard,pie,statics等)"   
    private Integer menu_id;  //"所属菜单id"   
    private Integer module_id;  //"所属模块id" 
    @JSONField(jsonDirect =  true )
    private String meta;  //"扩展信息"   
    @JSONField(jsonDirect =  true )
    private String clients;  //"支持客户端[pc_cn/pc_mn/app_cn/app_mn]"   
    private Integer status;  //"启用状态"   
    private Date created_at;  //"创建时间"
    private Date updated_at;  //"更新时间"
    private Date deleted_at;  //"删除时间"
}
