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
 * 德育量化项目表
 * </p>
 *
 * @author Orhon
 */
@TableName("moral_item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Item extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"德育量化项目id"   
private Integer school_id;  //"学校id"   
private Integer sort;  //"排序"   
private Integer type;  //"1.学校层2.年级层"   
private Integer classcheck;  //"参与班级考核分1、参与2、不参与"   
private Integer studentcheck;  //"参与学生考核分1、参与2、不参与"   
private Integer teacherscheck;  //"参与班主任考核分1、参与2、不参与"   
private Integer teachercheck;  //"参与教职工考核分1、参与2、不参与"   
private String fromgroup;  //"套餐fromgroup"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识"   
private String classcheck_percent;    
private String studentcheck_percent;    
private Integer template_type;  //"1普通模板2宿舍模板"   
private Integer parent_id;  //"父级id"   
private Integer score_type;  //"1.集体2.个人"
@JSONField(jsonDirect=true)
private String moral_education_name; 
}
