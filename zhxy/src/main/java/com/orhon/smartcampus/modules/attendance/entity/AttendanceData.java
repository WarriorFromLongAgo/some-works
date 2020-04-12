package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * <p>
 * 考勤数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_data")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Valid
public class AttendanceData extends BaseModel {

    private static final long serialVersionUID = 1L;


    private Integer id;  //"自增id"   
    private Integer school_id;  //"学校ID"   
    private Long user_id;  //"用户id"
    private String idcard;  //"身份证号"
    private String device_ip;  //"设备ip"   
    private String auth_time;  //"打卡时间"   
    private String month;  //"年-月"   
    private Integer alert;  //"考勤状态 1：正常2：迟到3：矿工4：早退5：下班考勤6：请假7：无需打卡 9：缺卡"   
    private LocalDateTime created_at;  //"创建时间"   
    private LocalDateTime updated_at;  //"更新时间"   
    private LocalDateTime deleted_at;  //"删除时间"   
    private Integer semester_id;  //"学期"   
    private Integer schoolyear_id;  //"学年"
    private LocalTime time_an;  //"考勤时间段-开始"
    private LocalTime time_en;  //"考勤时间段-结束"
    private Integer attendance_group_time_id;  //"考勤时间id"   
    private Integer attendance_group_id;  //"考勤组ID"
}
