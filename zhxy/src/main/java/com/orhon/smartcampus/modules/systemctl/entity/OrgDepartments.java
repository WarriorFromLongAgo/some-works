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
 *
 * </p>
 *
 * @author Orhon
 */
@TableName("systemctl_org_departments")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrgDepartments extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"模块ID"
    private Integer school_id;  //"学校id"   
    private String type;  //"类型"   
    private Integer parent_id;  //"父级id"   
    private String group_type;  //"组类型"   
    private Integer department_order;  //"排序"
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    @JSONField(jsonDirect =  true )
    private String department_name;   //"部门名称"
    private Integer status;  //"部门状态"
}
