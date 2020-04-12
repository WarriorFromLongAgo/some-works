package com.orhon.smartcampus.modules.document.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
@TableName("document_teacher")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Teacher extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"通知人id"   
private Integer department_id;  //"部门id"   
private Integer user_id;  //"用户id"
private Integer from_user_id;  //"下发人"   
private Integer document_id;  //"公文id"   
private Integer is_look;  //"查看状态（0：未查看1：已查看）"   
private LocalDateTime deleted_at;    
private LocalDateTime created_at;    
private Integer created_by;    
private LocalDateTime updated_at;    
private Integer updated_by;
@JSONField(jsonDirect=true)
private String content; 
}
