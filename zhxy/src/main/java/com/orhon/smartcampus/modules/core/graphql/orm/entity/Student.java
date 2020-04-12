package com.orhon.smartcampus.modules.core.graphql.orm.entity;


import java.util.Date;

import javax.persistence.*;


import lombok.Data;





/**
 * <p>
 * 学生基础信息表
 * </p>
 *
 * @author bao
 */

@Data
@Entity
@Table(name="student_information")
public class Student{

	@Id
    private Integer id;  //"学生基础信息id"
	
	@OneToOne( cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	
	@Column(name = "student_number")
    private String student_number;  //"学籍号"
    
	@Column(name = "student_learncode")
    private String student_learncode;  //"学生学号"
	
	@Column(name = "student_name")
    private String student_name;  //"学生姓名"
	
	@Column(name = "used_name")
    private String used_name;  //"曾用名"
	
	@Column(name = "pinyin_name")
    private String pinyin_name;  //"姓名拼音"
	
	@Column(name = "country")
    private String country;  //"国籍(数据字典: nationality)"
	
	@Column(name = "idcard")
    private String idcard;  //"身份证号"
	
	@Column(name = "birthday")
    private String birthday;  //"出生日期"
	
	@Column(name = "sex")
    private String sex;  //"性别(数据字典: gender)"
	
	@OneToOne( cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;  //"学校id"
	
	@Column(name = "picture")
    private String picture;  //"照片"
	
	@Column(name = "native_place")
    private String native_place;  //"籍贯"
	
	@Column(name = "nation")
    private String nation;  //"民族(数据字典:nation)"
	
	@Column(name = "hkmtb")
    private Integer hkmtb;  //"是否港澳台侨(0,1)"
	
	@Column(name = "political")
    private String political;  //"政治面貌(数据字典: politicaltype)"
	
	@Column(name = "speciality")
    private String speciality;  //"特长"
	
	@Column(name = "identity_ivalidity")
    private String identity_ivalidity;  //"身份证有效期"
	
	@Column(name = "blood")
    private String blood;  //"血型"
	
	@Column(name = "tel_number")
    private String tel_number;  //"手机号"
	
	@Column(name = "email")
    private String email;  //"邮箱"
	
	@Column(name = "postal_code")
    private String postal_code;  //"邮编"
	
	@Column(name = "home_page")
    private String home_page;  //"个人主页地址"
	
	@Column(name = "household_register_type")
    private String household_register_type;  //"户口性质(数据字典：household_register_type)"

	@Column(name = "birth_place")
    private String birth_place;  //"出生地"
	
	@Column(name = "present_address")
    private String present_address;  //"现住址"
	
	@Column(name = "corresponding_address")
    private String corresponding_address;  //"通讯地址"
	
	@Column(name = "family_address")
    private String family_address;  //"家庭住址"
	
	@Column(name = "household_registed_id")
    private Integer household_registed_id;  //"户籍所在地-省"
	
	@Column(name = "household_registed_city_id")
    private Integer household_registed_city_id;  //"户籍所在地-市"
	
	@Column(name = "household_registed_county_id")
    private Integer household_registed_county_id;  //"户籍所在地-旗县"
	
	@Column(name = "cradkind_id")
    private String cradkind_id;  //"证件类型"
	
	@Column(name = "is_onlychild")
    private Integer is_onlychild;  //"是否独生子女"
	
	@Column(name = "is_pre_school_education")
    private Integer is_pre_school_education;  //"是否经过学前教育"
	
	@Column(name = "is_left_behind_children")
    private Integer is_left_behind_children;  //"是否留守儿童"
	
	@Column(name = "is_orphan")
    private Integer is_orphan;  //"是否孤儿"
	
	@Column(name = "is_martyr")
    private Integer is_martyr;  //"是否是烈士或优抚子女"
	
	@Column(name = "is_study_in_class")
    private Integer is_study_in_class;  //"是否随班就读"
	
	@Column(name = "is_government_degree")
    private Integer is_government_degree;  //"是否由政府购买学位"
	
	@Column(name = "is_funding")
    private Integer is_funding;  //"是否需要申请过资助"
	
	@Column(name = "is_supplement")
    private Integer is_supplement;  //"是否享受一补"
	
	@Column(name = "is_move_with")
    private Integer is_move_with;  //"是否迁随子女"

	@Column(name = "health")
    private String health;  //"健康状态(数据字典：health)"
    
    @Column(name = "disabled_status")
    private String disabled_status;  //"残疾人类型(数据字典：disabled_status)"
    
    @Column(name = "created_at")
    private Date created_at;  //"创建时间"
    
    @Column(name = "updated_at")
    private Date updated_at;  //"修改时间"
    
    @Column(name = "deleted_at")
    private Date deleted_at;  //"删除标识"


}
