package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * <p>
 * 考勤组周设置数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_week")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceWeek extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField
    private Integer id;  //"自增ID"   
    private Integer attendance_group_id;  //"考勤组ID"
    private String week;  //"周"
    private LocalDateTime updated_at;  //"更新时间"
    private LocalDateTime created_at;  //"创建时间"
    private Integer created_by;  //"创建时间"
    private Integer updated_by;  //"创建时间"
    private Integer deleted_at;  //"删除时间"
}
