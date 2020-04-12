package com.orhon.smartcampus.modules.watchlist.entity;

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
 * 数据添加
 * </p>
 *
 * @author Orhon
 */
@TableName("watchlist_data_log")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Data_log extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"数据添加logid"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"   
    private LocalDateTime deleted_at;  //"删除标识"   
    private Integer school_id;  //"学校id"   
    private Integer leader;  //"值班领导"   
    private String leader_remarks; 
}
