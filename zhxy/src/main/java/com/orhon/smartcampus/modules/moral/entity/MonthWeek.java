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
@TableName("moral_month_week")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MonthWeek extends BaseModel {

private static final long serialVersionUID=1L;

    @TableId
    private Integer id;  //"主键id"   
    private Integer month_id;  //"月份id"   
    private Integer week_id;  //"周id"   
    private LocalDateTime created_at;  //"添加时间"   
    private LocalDateTime updated_at;  //"修改时间"
}
