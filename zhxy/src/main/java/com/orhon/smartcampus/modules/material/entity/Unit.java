package com.orhon.smartcampus.modules.material.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 单元表
 * </p>
 *
 * @author Orhon
 */
@TableName("material_unit")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Unit extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"单元id"   
    private Integer buildings_id;  //"楼宇id"   
    private String unit_code;  //"单元编号"   
    private String unit_tel;  //"单元电话"   
    private Integer floor_num;  //"楼层总数"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"   
    private LocalDateTime deleted_at;  //"删除标识"   
    private Integer school_id;  //"学校id"
    @JSONField(jsonDirect=true)
    private String unit_name;
    @JSONField(jsonDirect=true)
    private String unit_manager; 
}
