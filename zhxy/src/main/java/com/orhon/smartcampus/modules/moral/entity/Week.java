package com.orhon.smartcampus.modules.moral.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
@TableName("moral_week")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Week extends BaseModel {

private static final long serialVersionUID=1L;

    @TableId
    private Integer id;  //"主键id"   
    private Integer schoolyear_id;  //"学年id"   
    private Integer semester_id;  //"学期id"   
    private LocalDate start_time;  //"开始时间"   
    private LocalDate end_time;  //"结束时间"   
    private String cycle;  //"周次"   
    private LocalDateTime created_at;  //"添加时间"   
    private LocalDateTime updated_at;  //"修改时间"   
    private LocalDateTime deleted_at;  //"删除时间"
}
