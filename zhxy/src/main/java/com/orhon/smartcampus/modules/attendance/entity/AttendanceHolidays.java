package com.orhon.smartcampus.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * <p>
 * 节假日管理
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_holidays")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceHolidays extends BaseModel {

    private static final long serialVersionUID = 1L;


    private Integer id;  //"主键id"   
    private String year;  //"年"
    private String month;  //"月"   
    private String day;  //"日"
    private String specificdate;  //"年月日"
    private Integer type;  //"类型1休息日2法定节假日"   
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Long created_by;
    private Long updated_by;
    private String title;  //"名称"
}
