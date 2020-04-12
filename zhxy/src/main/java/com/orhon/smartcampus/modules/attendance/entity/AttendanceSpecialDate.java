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
 * 特殊需要上班日期数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_special_date")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceSpecialDate extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField
    private Integer id;  //"主键id"   
    private String title;  //"名称"
    private String specificdate;  //"年-月-日"   
    private LocalDateTime created_at;
    private Integer created_by;
    private LocalDateTime updated_at;
    private Integer updated_by;
    private Integer deleted_at;
}
