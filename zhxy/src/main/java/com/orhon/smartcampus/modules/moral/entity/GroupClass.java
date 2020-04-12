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
 * 分组班级关系表
 * </p>
 *
 * @author Orhon
 */
@TableName("moral_group_class")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupClass extends BaseModel {

private static final long serialVersionUID=1L;

    @TableId
    private Integer id;  //"主键id"   
    private Integer group_id;  //"分组id"   
    private Integer class_id;  //"班级id"   
    private LocalDateTime created_at;  //"添加时间"   
    private LocalDateTime updated_at;  //"修改时间"
}
