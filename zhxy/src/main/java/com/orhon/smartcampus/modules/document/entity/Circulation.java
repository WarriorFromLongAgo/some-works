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
@TableName("document_circulation")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Circulation extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"公文id"   
private Integer document_receive_writing;    
private Integer semester_id;  //"学期id"   
private Integer examine_user;  //"审核人"   
private String document_code;  //"文号"   
private String receipt_time;  //"收文时间"   
private Integer examine_status;  //"审核状态（0：未审核1：通过审核2：未通过审核）"   
private Integer type;  //"通知类型"   
private LocalDateTime created_at;  //"发起人"   
private Integer created_by;  //"发起时间"   
private LocalDateTime updated_at;  //"修改人"   
private Integer updated_by;  //"修改时间"
private Integer school_id;  //"学校id"   
private LocalDateTime deleted_at;
@JSONField(jsonDirect=true)
private String title;  //"标题" 
@JSONField(jsonDirect=true)
private String content;  //"内容"
@JSONField(jsonDirect=true)
private String civil_service;  //"来问机关" 
@JSONField(jsonDirect=true)
private String letter_number;  //"来文字号"
@JSONField(jsonDirect=true)
private String grade;  //"等级"   
@JSONField(jsonDirect=true)
private String options;  //"你办意见"
}
