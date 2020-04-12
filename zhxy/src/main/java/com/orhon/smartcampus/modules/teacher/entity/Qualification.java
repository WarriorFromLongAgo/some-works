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
@TableName("teacher_qualification")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Qualification extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"专职资格id"   
private Integer teacher_id;  //"教职工id"   
private Integer school_id;  //"学校id"   
private Integer experttype;  //"专家类别id"   
private Integer tradeid;  //"行业类别id"   
private Integer inputstyle;  //"资格id"   
private Integer jobtypeid;  //"现从事专业id"   
private LocalDateTime takedate;  //"取得时间"   
private Integer jtlevelid;  //"等级id"   
private String certificateno;  //"证书编号"   
private String image;  //"证书扫描件(上传图片)"   
private Integer isemploy;  //"是否聘任(单选)1、不是2、是"   
private Integer shenhe;  //"审核(单选1、未审核2、审核通过)"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识" 
@JSONField(jsonDirect=true)
private String awarddept;  //"授予部门"
}
