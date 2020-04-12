package com.orhon.smartcampus.modules.material.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 楼宇表
 * </p>
 *
 * @author Orhon
 */
@TableName("material_buildings")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Buildings extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"楼宇Id"   
    private String buildings_code;  //"楼宇编号"   
    private Integer campus_id;  //"校区id"   
    private Integer site_id;  //"场地id"   
    private Integer school_id;  //"学校id"   
    private Integer unit_num;  //"总单元"   
    private Integer floor_num;  //"楼层id"   
    private Integer buildings_status;  //"房屋状态"   
    private LocalDate buildings_at;  //"楼建立时间"   
    private Integer buildings_years;  //"使用年限"   
    private String buildings_tel;  //"楼管电话"   
    private Integer buildings_use;  //"建筑用途"   
    private Double buildings_cost;  //"建筑费用（选填）"   
    private Double buildings_area;  //"楼宇面积"   
    private Double buildings_onarea;  //"占用面积"   
    private LocalDateTime deleted_at;  //"删除标识"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"
    @JSONField(jsonDirect=true)
    private String buildings_name;
    @JSONField(jsonDirect=true)
    private String buildings_property;
    @JSONField(jsonDirect=true)
    private String buildings_material;
    @JSONField(jsonDirect=true)
    private String bulidings_introduction; 
}
