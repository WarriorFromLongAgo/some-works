package com.orhon.smartcampus.modules.student.entity;

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
 * 班级表
 * </p>
 *
 * @author Orhon
 */
@TableName("student_eclass")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Eclass extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"班级id"   
private Integer school_id;  //"学校id"   
private Integer grade_id;  //"年级id"   
private Integer arrives_id;  //"届id"   
private String eclass_code;  //"班级编号"   
private Integer class_status;  //"状态"   
private Integer class_kind_dict;  //"班级类型"   
private Integer class_nature;  //"班级性质"   
private Integer buildings_id;  //"楼宇id"   
private Integer unit_id;  //"单元id"   
private Integer floor_id;  //"楼层id"   
private Integer classroom;  //"教室"   
private Integer graduated_flag;  //"毕业标识"   
private Integer eclass_order;  //"班级排序"   
private String eclass_number;  //"班级号"   
private String ioc_path;  //"班级图标"   
private String eclass_pic;  //"班级图片"   
private Date created_at;  //"创建时间"
private Integer created_by;  //"创建人"   
private Date updated_at;  //"修改时间"
private Integer updated_by;  //"修改人"   
private Date deleted_at;  //"删除标识"
private String logo;  //"班级logo" 
@JSONField(jsonDirect=true)
private String eclass_name;  //"班级名称"   
@JSONField(jsonDirect=true)
private String introduction;  //"班级简介" 
@JSONField(jsonDirect=true)
private String teacher_comment;  //"教师寄语" 
@JSONField(jsonDirect=true)
private String eclass_slogan;  //"班级口号" 
@JSONField(jsonDirect=true)
private String eclass_rota;  //"班级值日表"  
@JSONField(jsonDirect=true)
private String headteacher_message;  //"班主任寄语"

}
