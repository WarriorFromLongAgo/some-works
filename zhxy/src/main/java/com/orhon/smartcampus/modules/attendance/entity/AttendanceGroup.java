package com.orhon.smartcampus.modules.attendance.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * <p>
 * 考勤组数据表
 * </p>
 *
 * @author Orhon
 */
@TableName("attendance_group")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Valid
public class AttendanceGroup extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField
    private Integer id;
    @NotNull(message = "学校ID不能为空")
    private Integer school_id;  //"学校ID"
    @NotBlank(message = "标题不能为空")
    @JSONField(jsonDirect = true)
    private String title;  //"标题"
    private LocalDateTime created_at;  //"创建时间"
    private Integer created_by;  //"创建用户ID"
    private LocalDateTime updated_at;  //"修改时间"
    private Integer updated_by;  //"最后修改用户ID"
    @TableLogic
    private Integer deleted_at;//"软删除" 0 未删除 1 删除
}
