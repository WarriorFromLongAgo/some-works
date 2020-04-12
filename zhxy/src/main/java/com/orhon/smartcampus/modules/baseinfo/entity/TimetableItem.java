package com.orhon.smartcampus.modules.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 作息时间表详情
 * </p>
 *
 * @author Orhon
 */
@TableName("baseinfo_timetable_item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TimetableItem extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"作息表详细id"   
private Integer semester_id;  //"学期id"   
private Integer class_type;  //"第几节课"   
private Integer start_week;  //"第几周开始"   
private Integer end_week;  //"第几周结束"   
private String time;  //"时间"   
private Integer noon_rest;  //"是否有午休（0：无1：有）"   
private Integer created_by;  //"创建人"   
private LocalDateTime created_at;  //"创建日期"   
private Integer updated_by;  //"最后修改人"   
private LocalDateTime updated_at;  //"最后修改日期"   
private Integer school_id;  //"学校id"   
private LocalDateTime deleted_at;
}
