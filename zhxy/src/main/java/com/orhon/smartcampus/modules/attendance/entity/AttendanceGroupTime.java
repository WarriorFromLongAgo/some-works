package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * <p>
 * 考勤组时间数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_group_time")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceGroupTime extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField
    private Integer id;  //"id"
    @NotNull(message = "学校ID不能为空")
    private Integer school_id;
    @NotNull(message = "考勤组ID不能为空")
    private Integer attendance_group_id;
    @NotNull(message = "学期ID不能为空")
    private Integer semester_id;  //"学期"
    @NotNull(message = "学年ID不能为空")
    private Integer schoolyear_id;  //"学年"
    @NotNull(message = "开始时间ID不能为空")
    private String time_an;  //"开始时间"
    @NotNull(message = "结束时间ID不能为空")
    private String time_en;  //"结束时间"
    @NotNull(message = "time_type不能为空")
    private Integer time_type;  //"1，上午2，中午3、下午4、晚自习"
    @NotNull(message = "考勤类型不能为空")
    private Integer attendance_type;  //"考勤类型（1：上班考勤2：迟到3：矿工4：早退5：下班考勤）	"
    private LocalDateTime created_at;
    private Integer created_by;
    private LocalDateTime updated_at;
    private Integer updated_by;
    private Integer deleted_at;
}
