package com.orhon.smartcampus.modules.document.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("document_student")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Student extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"学生查看公文id"   
private Integer user_id;  //"用户id"   
private Integer eclass_id;  //"班级id"   
private Integer grade_id;  //"年级id"   
private Integer document_id;  //"公文id"   
private Integer is_look;  //"是否查看（0：未查看1：查看）"   
private LocalDateTime deleted_at; 
}
