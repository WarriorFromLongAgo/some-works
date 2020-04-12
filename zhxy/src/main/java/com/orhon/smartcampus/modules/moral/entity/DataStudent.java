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
 * 
 * </p>
 *
 * @author Orhon
 */
@TableName("moral_data_student")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataStudent extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"id"   
private Integer moral_column_id;  //"表单数据值id"   
private Integer user_id;  //"学生id"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识"   
@JSONField(jsonDirect=true)
private String remarks; 
}
