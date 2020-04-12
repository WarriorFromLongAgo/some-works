package com.orhon.smartcampus.modules.moral.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("moral_basic")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Basic extends BaseModel {

private static final long serialVersionUID=1L;

    @TableId
    private Integer id;  //"月id"   
    private Integer points;  //"分数"   
    private Integer school_id;  //"学校id"   
    private Integer schoolyear_id;  //"学年id"   
    private Integer semester_id;  //"学期id"   
    private LocalDateTime created_at;  //"添加时间"   
    private LocalDateTime updated_at;  //"修改时间"   
    private LocalDateTime deleted_at;  //"删除时间"
}
