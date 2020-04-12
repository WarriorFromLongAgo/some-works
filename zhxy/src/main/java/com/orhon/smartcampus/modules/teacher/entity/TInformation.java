package com.orhon.smartcampus.modules.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 教职工表
 * </p>
 *
 * @author Orhon
 */
@TableName("teacher_information")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TInformation extends BaseModel {

private static final long serialVersionUID=1L;

@TableId
private Integer id;  //"教职工id"   
private Integer user_id;  //"用户ID"   
private String idcard;  //"身份证号"   
private String gender;  //"性别"
private String birthday;  //"出生日期"
private Integer nativeprovince;  //"籍贯-省"   
private Integer nativecity;  //"籍贯-市"   
private Integer nativecounty;  //"籍贯-县"   
private Integer birthprovince;  //"出生地-省"   
private Integer birthcity;  //"出生地-市"   
private Integer birthcounty;  //"出生地-县"   
private String type;  //"人才类型"
private Integer unitname;  //"单位名称（学校ID）"   
private String email;  //"电子邮箱"   
private String telnumber;  //"联系电话"   
private String nationality;  //"国际"
private String politicaltype;  //"政治面貌"
private String workstatus;  //"在岗状态"
private String workdate;  //"参加工作时间"
private String jobtitle;  //"最高职称"
private String education;  //"最高学历"
private String degree;  //"最高学位"
private String mastersupervisor;  //"是否硕导"
private String teach;  //"是否博导"
private String talentcategory_six;  //"人才类别(按六支队伍分)"
private String talentcategory_nine;  //"人才类别(按九支队伍分)"
private Integer cmbprovince3;  //"现住址-省"   
private Integer cmbcity3;  //"现住址-市"   
private Integer cmbcounty3;  //"现住址-县"   
private Integer cmbprovince2;  //"户籍所在地-省"   
private Integer cmbcity2;  //"户籍所在地-市"   
private Integer cmbcounty2;  //"户籍所在地-县"   
private Integer school_id;  //"学校id"   
private Date created_at;  //"创建时间"
private Integer created_by;  //"创建人"   
private Date updated_at;  //"修改时间"
private Integer updated_by;  //"修改人"   
private Date deleted_at;  //"删除标识"
private String nation;  //"民族"
private String imgphotos;  //"公安身份证备案照片"   
private String signimg;  //"本人签字图片"  
@JSONField(jsonDirect=true)
private String teacher_name;    
@JSONField(jsonDirect=true)
private String introduction;
}
