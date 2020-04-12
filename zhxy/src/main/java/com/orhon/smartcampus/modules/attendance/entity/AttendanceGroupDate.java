package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * <p>
 * 考勤时间段数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_group_date")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceGroupDate extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField
    private Integer id;  //"自增ID"
    @NotNull(message = "考勤组ID不能为空")
    private Integer attendance_group_id;  //"考勤组ID"
    @NotBlank(message = "考勤开始日期不能为空")
    private String start_at;  //"考勤开始日期"
    @NotBlank(message = "考勤结束日期不能为空")
    private String end_at;  //"考勤结束日期"
    private LocalDateTime created_at;
    private Integer created_by;
    private LocalDateTime updated_at;
    private Integer updated_by;
    private Integer deleted_at;
}
