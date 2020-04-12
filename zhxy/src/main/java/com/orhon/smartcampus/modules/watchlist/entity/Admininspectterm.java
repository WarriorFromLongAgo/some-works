package com.orhon.smartcampus.modules.watchlist.entity;

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
 * 行政值班检查项
 * </p>
 *
 * @author Orhon
 */
@TableName("watchlist_admininspectterm")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Admininspectterm extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"行政值班检查项id"   
    private Integer administrativeduty_id;  //"行政值班项id"   
    private Integer school_id;  //"学校id"   
    private Integer pid;  //"pid上级id"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"   
    private Integer order;  //"排序"   
    private LocalDateTime deleted_at;  //"删除标识"
    @JSONField(jsonDirect=true)
    private String admininspectterm_name; 
}
