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
@TableName("document_appendix")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Appendix extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"通知附件表id"   
private Integer document_id;  //"公文id"   
private String content;  //"附件内容"   
private String file_name;    
private LocalDateTime deleted_at;  
}
