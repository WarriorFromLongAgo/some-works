package com.orhon.smartcampus.modules.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * VIEW
 * </p>
 *
 * @author Orhon
 */
@TableName("student_eclass_grade_view")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Eclass_grade_view extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer grade_id;  //"所属年级"   
    private Integer eclass_id;  //"班级id"   
    private Integer school_id;  //"学校id"
}
