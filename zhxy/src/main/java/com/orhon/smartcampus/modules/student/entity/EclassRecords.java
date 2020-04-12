package com.orhon.smartcampus.modules.student.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.beans.Transient;
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
 * @author bao
 */
@TableName("student_eclass_records")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EclassRecords extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;  //"主键id"
    private Integer original_eclass;  //"原班id"
    private Integer go_eclass;  //"去向班id"
    private Integer transaction_type;  //"异动状态"
    private LocalDateTime eclass_time;  //"调班时间"
    private Integer user_id;  //"用户id"
    @JSONField(jsonDirect = true)
    private String reason;  //"原因"
    private String status;  //"‘normal正常’’,’’graduation毕业’’,’’abnormal异常’’,’’transferred：调班’’’,"
    private LocalDateTime created_at;  //"创建时间"
    private Integer created_by;  //"创建人"
    private LocalDateTime updated_at;  //"修改时间"
    private Integer updated_by;  //"修改人"
    private Integer deleted_at;  //"删除标识"
    private Integer student_id;  //"学生id"





}