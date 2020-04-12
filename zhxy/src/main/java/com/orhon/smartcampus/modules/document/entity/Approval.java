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
@TableName("document_approval")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Approval extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;    
private Integer document_id;    
private Integer user_id;    
private Integer created_by;    
private LocalDateTime created_at;    
private Integer updated_by;    
private LocalDateTime updated_at;    
private LocalDateTime deleted_at; 
@JSONField(jsonDirect=true)
private String approval; 
}
