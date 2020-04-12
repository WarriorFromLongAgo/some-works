package com.orhon.smartcampus.modules.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学校学科关联
 * </p>
 *
 * @author Orhon
 */
@TableName("baseinfo_school_subjects")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SchoolSubjects extends BaseModel {

private static final long serialVersionUID=1L;
@TableId
private Integer id;    
private Integer school_id;  //"学校id"   
private Integer subject_id;  //"学科id"
}
