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
 * 场地
 * </p>
 *
 * @author Orhon
 */
@TableName("material_site")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Site extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"场地id"   
    private Integer campus_id;  //"校区id"   
    private Integer site_use;  //"场地用途（选择）"   
    private Double site_area;  //"场地面积"   
    private Integer site_right_use;  //"场地使用权（1.学校2外借）"   
    private String site_tel;  //"场地联系方式"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"   
    private Integer school_id;  //"学校id"   
    private LocalDateTime deleted_at;  //"删除标识"   
    private String site_name;    
    private String site_manager; 
}
