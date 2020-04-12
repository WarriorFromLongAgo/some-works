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
 * 特殊情况不需要打卡数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_special_personnel")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceSpecialPersonnel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField
    private Integer id;  //"自增ID"
    private String title;  //"特殊原因"
    private Integer user_id;  //"用户ID"
    private String start_at;  //"不需要考勤开始日期"
    private String end_at;  //"不需要考勤结束日期"
    private LocalDateTime updated_at;  //"更新时间"
    private LocalDateTime created_at;  //"创建时间"
    private Integer created_by;  //"创建时间"
    private Integer updated_by;  //"创建时间"
    private Integer deleted_at;  //"删除时间"
}
