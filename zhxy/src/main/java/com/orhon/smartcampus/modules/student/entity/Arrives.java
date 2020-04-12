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
 * 届
 * </p>
 *
 * @author Orhon
 */
@TableName("student_arrives")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Arrives extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;    
private Integer grade_id;  //"所属年级"   
private Integer period_id;  //"所属学段"   
private Integer arrives_order;  //"显示顺序"   
private Integer school_id;  //"学校"   
private Date created_at;  //"创建时间"
private Date updated_at;  //"更新时间"
private Date deleted_at;  //"删除时间"
private String mark;  //"标识"
@JSONField(jsonDirect=true)
private String name;  //"名称"
private String begin_year;  //"名称"
private String graduate_year;  //"名称"
private Integer garduate_status;  //"名称"

}
