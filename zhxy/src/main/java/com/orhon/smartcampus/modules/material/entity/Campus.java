package com.orhon.smartcampus.modules.material.entity;

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
 * 校区管理
 * </p>
 *
 * @author Orhon
 */
@TableName("material_campus")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Campus extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"校区ID"   
    private Integer school_id;  //"学校ID"   
    private String campus_area;  //"校区占用面积"   
    private String campus_tel;  //"校区联系电话"   
    private String campus_name;  //"校区名称"   
    private String campus_manager;  //"校区管理人"   
    private String campus_position;  //"校区位置信息"   
    private LocalDateTime created_at;    
    private Integer created_by;    
    private LocalDateTime updated_at;    
    private Integer updated_by;    
    private LocalDateTime deleted_at; 
}
