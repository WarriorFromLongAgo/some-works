package com.orhon.smartcampus.modules.material.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
 * 楼层
 * </p>
 *
 * @author Orhon
 */
@TableName("material_floor")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Floor extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"楼层id"   
    private Integer unit_id;  //"单元id"   
    private Integer school_id;  //"学校id"   
    private String floor_code;  //"楼层编号"   
    private String floor_tel;  //"楼层电话"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"   
    private LocalDateTime deleted_at;  //"删除标识"
    @JSONField(jsonDirect=true)
    private String floor_manager;  //"楼层负责人"
    @JSONField(jsonDirect=true)
    private String floor_name;  //"楼层名"
}
