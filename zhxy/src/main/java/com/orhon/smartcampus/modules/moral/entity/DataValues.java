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
@TableName("moral_data_values")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataValues extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"表单数据值id"   
private Integer moral_education_id;  //"德育量化项目id"   
private Integer classcheck;  //"参与班级考核分1、参与2、不参与"   
private Integer studentcheck;  //"参与学生考核分1、参与2、不参与"   
private Integer teacherscheck;  //"参与班主任考核分1、参与2、不参与"   
private Integer teachercheck;  //"参与教职工考核分1、参与2、不参与"   
private Integer moral_rules_id;  //"量化细则id"   
private Double fixed_score;  //"细则固定分值"   
private Integer classification;  //"分类(1.加分2.减分)"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private Integer score_type;  //"1.集体2.个人"   
private Integer class_type;  //"1.班级2.宿舍"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识"   
private Integer school_id;  //"学校id"   
private Integer semester_id;  //"学期id"   
private Integer schoolyear_id;  //"学年id"   
private Integer grade_id;  //"年级id"   
private Integer section_id;  //"第几节"   
private Integer user_id;  //"班主任id"   
private Integer class_id;  //"班级id"   
private Integer numberpeople;  //"人数"   
private Integer dormitory_id;  //"宿舍id"   
private String moral_type;    
private Integer template_type;  //"0普通模板1宿舍模板"   
private String teacher_score;    
private Integer type;  //"1.德育数据2：学生德育"  
@JSONField(jsonDirect=true)
private String remarks; 
}
