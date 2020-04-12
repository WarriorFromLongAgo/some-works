package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 考勤教师数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_teacher")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceTeacher extends BaseModel {

private static final long serialVersionUID=1L;

    @TableField
    private Integer id;  //"自增ID"   
    private Integer attendance_group_id;  //"考勤组ID"   
    private String user_id;  //"用户ID"
    private Integer department_id;//部门ID
    private Integer created_by;  //"创建人"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer updated_by;  //"更新人"   
    private LocalDateTime updated_at;  //"更新时间"   
    private LocalDateTime deleted_at;  //"删除时间"
}
