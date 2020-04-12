package com.orhon.smartcampus.modules.attendance.entity;

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
 * 无需打卡人员
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_no_need_attendance_personnel")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceNoNeedAttendancePersonnel extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"自增ID"   
    private Integer user_id;  //"用户ID"   
    private LocalDateTime updated_at;  //"更新时间"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer deleted_at;  //"删除时间"   
    private Integer created_by;    
    private Integer updated_by; 
}
