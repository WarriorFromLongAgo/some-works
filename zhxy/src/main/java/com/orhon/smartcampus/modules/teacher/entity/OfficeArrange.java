package com.orhon.smartcampus.modules.teacher.entity;

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
@TableName("teacher_office_arrange")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OfficeArrange extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
    private Integer id;  //"办公室房间分配"
    private String  office_name;  //"办公室名称"
    private Integer buildings_id;  //"楼宇id"
    private Integer unit_id;  //"单元id"
    private Integer floor_id;  //"楼层id"
    private Integer room_id;  //"房间id"
    private Integer department_id;  //"部门id"
    private Integer head_user;  //"负责人id"
    private Date created_at;  //"添加时间"
    private Integer created_by;  //"添加人"
    private Date updated_at;  //"修改时间"
    private Integer updated_by;  //"修改人"
    private Integer school_id;  //"学校id"
    private Date deleted_at;
}
