package com.orhon.smartcampus.modules.attendance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * VIEW
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_statistics_semester")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceStatisticsSemester extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer user_id;  //"用户id"   
    private Integer semester_id;  //"学期"   
    private String month;  //"年-月"   
    private BigDecimal one;    
    private BigDecimal two;    
    private BigDecimal three;    
    private BigDecimal four;    
    private BigDecimal five;    
    private BigDecimal six;    
    private BigDecimal seven;    
    private BigDecimal eight; 
}
