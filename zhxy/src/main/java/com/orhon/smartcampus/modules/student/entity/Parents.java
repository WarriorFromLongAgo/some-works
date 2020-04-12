package com.orhon.smartcampus.modules.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学生家长关系表
 * </p>
 *
 * @author Orhon
 */
@TableName("student_parents")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Parents extends BaseModel {

	private static final long serialVersionUID=1L;

	@TableId
	private Integer id;  //"学生家长关系id"   
	private Integer user_id;  //"学生id"  
	@JSONField(jsonDirect=true)
	private String parent_name;  //"姓名"  
	@JSONField(jsonDirect=true)
	private String relation;  //"家长学生关系"   
	private String nation;  //"民族(数据字典:nation)"   
	private String gender;  //"性别(数据字典:gender)"   
	private String idcard;    
	private Integer is_guardian;  //"是否是监护人（1是2否）"   
	private String postal_address;  //"通讯地址"   
	private String picture;  //"照片"   
	private String tel_number;    
	private LocalDateTime created_at;  //"创建时间"   
	private LocalDateTime updated_at;  //"修改时间"   
	private LocalDateTime deleted_at;  //"删除标识"
}
