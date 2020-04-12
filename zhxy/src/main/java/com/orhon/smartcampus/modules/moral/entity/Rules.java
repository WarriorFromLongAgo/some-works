package com.orhon.smartcampus.modules.moral.entity;

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
 * 德育量化项目细则表
 * </p>
 *
 * @author Orhon
 */
@TableName("moral_rules")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rules extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"德育量化细则id"   
private Integer pid;  //"上级id"   
private Integer moral_education_id;  //"德育量化id"   
private Integer school_id;  //"学校id"   
private Integer classification;  //"分类(1.加分2.减分)"   
private Double fixed_score;  //"固定分值"   
private Double lowest_score;  //"酌情分最低分值"   
private Double highest_score;  //"酌情分最高分值"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识"   
@JSONField(jsonDirect=true)
private String entry_content;
@JSONField(jsonDirect=true)
private String remarks; 
}
