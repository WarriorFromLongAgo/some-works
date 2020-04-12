package com.orhon.smartcampus.modules.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 节假日管理
 * </p>
 *
 * @author Orhon
 */
@TableName("baseinfo_holidays")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Holidays extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"主键id"   
private Integer year;  //"年"   
private String month;  //"月"   
private String day;  //"月-日期"   
private String specificdate;  //"年月日"
private Integer type;  //"类型1休息日2法定节假日"   
private LocalDateTime created_at;    
private LocalDateTime updated_at;    
private String name;  //"名称"   
private Integer school_id; 
}
