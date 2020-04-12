package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * <p>
 * 教师请假数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_teacher_leave")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceTeacherLeave extends BaseModel {

    private static final long serialVersionUID = 1L;


    private Integer id;  //"教师请假id"   
    private Integer school_id;  //"学校id"   
    private Integer semester_id;  //"学期id"   
    private Integer user_id;  //"请假教师"   
    private String leave_days;  //"请假天数"   
    private Integer whether_class;  //"是否调课（1=是、2=否）已调课会有调课明细"   
    private Integer examine_status;  //"审核状态（0：未审核1：通过审核2：未通过审核）"   
    private String is_report_back;  //"是否销假，默认0"   
    private String report_back_name;  //"销假人"   
    private String report_back_time;  //"销假时间"   
    private Integer leave_kind;  //"请假种类id"   
    private String start_at;  //"开始时间"   
    private String end_at;  //"结束时间"   
    private LocalDateTime created_at;  //"创建日期"   
    private Long created_by;  //"创建人"
    private LocalDateTime updated_at;  //"最后修改日期"   
    private Long updated_by;  //"最后修改人"
    private Integer deleted_at;  //"软删除"
    private Integer parent_user_id;
}
