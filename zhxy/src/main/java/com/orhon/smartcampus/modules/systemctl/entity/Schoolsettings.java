package com.orhon.smartcampus.modules.systemctl.entity;

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
 * 学校设置
 * </p>
 *
 * @author Orhon
 */
@TableName("systemctl_schoolsettings")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Schoolsettings extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"ID"   
    private Integer school_id;  //"学校id"
    @JSONField(jsonDirect =  true )
    private String grades;  //"关联年级" 
    @JSONField(jsonDirect =  true )
    private String periods;  //"关联学段" 
    @JSONField(jsonDirect =  true )
    private String subjects;  //"关联学科" 
    @JSONField(jsonDirect =  true )
    private String courses;  //"关联课程" 
    @JSONField(jsonDirect =  true )
    private String platform_name;  //"平台名称"
    @JSONField(jsonDirect =  true )
    private String duties;  //"职务"
    private String logo;  //"学校logo"   
    private String favicon;  //"学校favicon" 
    @JSONField(jsonDirect =  true )
    private String theme_settings;  //"主题设置"   
    @JSONField(jsonDirect =  true )
    private String meta;  //"扩展信息"   
    private Date created_at;  //"创建时间"
    private Date updated_at;  //"更新时间"
    private Date deleted_at;  //"删除时间"

}
