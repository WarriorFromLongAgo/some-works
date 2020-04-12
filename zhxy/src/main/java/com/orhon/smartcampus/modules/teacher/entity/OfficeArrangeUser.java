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
@TableName("teacher_office_arrange_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OfficeArrangeUser extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"办公室分配教师"   
private Integer room_id;  //"房间id"   
private Integer user_id;  //"教师id"   
private Date created_at;  //"添加时间"
private Integer created_by;  //"添加人"   
private Date updated_at;  //"修改时间"
private Integer updated_by;  //"修改人"   
private Date deleted_at;
}
