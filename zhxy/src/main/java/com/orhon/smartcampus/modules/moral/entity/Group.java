package com.orhon.smartcampus.modules.moral.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 班级分组表
 * </p>
 *
 * @author Orhon
 */
@TableName("moral_group")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Group extends BaseModel {

private static final long serialVersionUID=1L;

    @TableId
    private Integer id;  //"主键id"   
    @JSONField(jsonDirect=true)
    private String title;  //"分组名称"   
    private LocalDateTime created_at;  //"添加时间"   
    private LocalDateTime updated_at;  //"修改时间"   
    private LocalDateTime deleted_at;  //"删除时间"
}
