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
@TableName("document_examine")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Examine extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"公文审核"   
private Integer document_id;  //"公文id"   
private Integer user_id;  //"用户id"   
private Integer status;  //"	审核状态（0：未审核，1：通过2：未通过3：向上级请示）"   
private Integer num;  //"审核次数"   
private Integer parent_id;  //"上级id"   
private LocalDateTime created_at;    
private Integer created_by;    
private LocalDateTime updated_at;    
private Integer updated_by;    
private LocalDateTime deleted_at; 
@JSONField(jsonDirect=true)
private String content;
}
