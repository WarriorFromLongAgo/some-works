package com.orhon.smartcampus.modules.material.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 房间
 * </p>
 *
 * @author Orhon
 */
@TableName("material_room")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Room extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"房间id"   
    private Integer floor_id;  //"楼层id"   
    private Double room_area;  //"房间面积"   
    private String room_code;  //"房间编号"   
    private Integer room_type;  //"使用分类（通用分类）"   
    private Integer key_num;  //"钥匙数量"   
    private Integer room_status;  //"房间状态"   
    private Date created_at;  //"创建日期"
    private Integer created_by;  //"创建人"   
    private Date updated_at;  //"修改日期"
    private Integer updated_by;  //"修改人"   
    private Integer school_id;  //"学校id"   
    private Date deleted_at;  //"删除标识（1=正常2=删除）"
    private String room_manager;  //"房间负责人"   
    private String keys_user_name;  //"钥匙负责人"
    @JSONField(jsonDirect=true)
    private String name;  //"房间名称"
    @JSONField(jsonDirect=true)
    private String remarks;  //"备注"
}
