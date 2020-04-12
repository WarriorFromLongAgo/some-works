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
@TableName("teacher_apcationalqualification")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Apcationalqualification extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"获得授权专利id"   
private Integer teacher_id;  //"教职工id"   
private Integer school_id;  //"学校id"   
private Integer type;  //"类别"   
private LocalDateTime impowerdate;  //"授权时间"   
private String image;  //"证书扫描件"   
private Integer shenhe;  //"审核(单选1、未审核2、审核通过)"   
private LocalDateTime created_at;  //"创建时间"   
private Integer created_by;  //"创建人"   
private LocalDateTime updated_at;  //"修改时间"   
private Integer updated_by;  //"修改人"   
private LocalDateTime deleted_at;  //"删除标识"   
@JSONField(jsonDirect=true)
private String name;  //"专利名称"  
@JSONField(jsonDirect=true)
private String impowernumber;  //"授权号"   
@JSONField(jsonDirect=true)
private String inventor;  //"发明人或设计人" 
@JSONField(jsonDirect=true)
private String nation;  //"授权国别或者组织"
}
