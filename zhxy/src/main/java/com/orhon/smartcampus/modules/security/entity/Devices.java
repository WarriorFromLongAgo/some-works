package com.orhon.smartcampus.modules.security.entity;

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
 * 
 * </p>
 *
 * @author Orhon
 */
@TableName("security_devices")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Devices extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;    
    private String ip;  //"ip"
    private String title;  //"位置信息"
    private LocalDateTime created_at;    
    private LocalDateTime updated_at;    
    private Integer created_by;    
    private Integer updated_by;    
    private Integer school_id;    
    private Integer type;  //"类型，1进2出"   
    private Integer device_types;  //"设备类型，1门禁2电子班牌3教职工考勤4宿舍门禁"
}
