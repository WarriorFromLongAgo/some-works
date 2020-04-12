package com.orhon.smartcampus.modules.teacher.entity;

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
 * 
 * </p>
 *
 * @author Orhon
 */
@TableName("teacher_ppcationalqualification")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ppcationalqualification extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"论文id"   
private Integer teacher_id;  //"教职工id"   
private Integer school_id;  //"学校id"   
private Integer core;  //"刊物是否为核心期刊"   
private Integer isinclude;  //"论文是否被收录"   
private Integer authornumber;  //"第几作者"   
private LocalDateTime publishdate;  //"发表时间"   
private String image;  //"证书扫描件"   
private Integer shenhe;  //"审核(单选1、未审核2、审核通过)"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识" 
@JSONField(jsonDirect=true)
private String title;  //"论文题目"
@JSONField(jsonDirect=true)
private String production;  //"发表刊物"
}
