package com.orhon.smartcampus.modules.baseinfo.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
 * 课节信息表
 * </p>
 *
 * @author Orhon
 */
@TableName("baseinfo_timetable")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Timetable extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;    
private Integer school_id;  //"学校id"   
private Integer semester_id;  //"学期id"   
private Integer type;  //"类型"   
private Integer orders;  //"顺序"   
private LocalDateTime created_at;    
private LocalDateTime updated_at;    
private Integer created_by;  //"添加人"   
private Integer updated_by;  //"修改人"   
private Integer deleted_at;
@JSONField(jsonDirect = true)
private String name; 
}
