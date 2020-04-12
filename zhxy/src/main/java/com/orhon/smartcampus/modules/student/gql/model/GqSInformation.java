package com.orhon.smartcampus.modules.student.gql.model;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class GqSInformation {

	private Integer id;  //"学生基础信息id"
    private Integer user_id;  //"用户id"
    private String student_number;  //"学籍号"
    private String student_learncode;  //"学生学号"
//    @JSONField(jsonDirect = true)
    private String student_name;  //"学生姓名"
//    @JSONField(jsonDirect = true)
    private String used_name;  //"曾用名"
    private String pinyin_name;  //"姓名拼音"
    private String country;  //"国籍(数据字典: nationality)"
    private String idcard;  //"身份证号"
    private String birthday;  //"出生日期"
    private String sex;  //"性别(数据字典: gender)"
    private Integer school_id;  //"学校id"
    private String picture;  //"照片"
//    @JSONField(jsonDirect = true)
    private String native_place;  //"籍贯"
    private String nation;  //"民族(数据字典:nation)"
    private Integer hkmtb;  //"是否港澳台侨(0,1)"
    private String political;  //"政治面貌(数据字典: politicaltype)"
//    @JSONField(jsonDirect = true)
    private String speciality;  //"特长"
    private String identity_ivalidity;  //"身份证有效期"
    private String blood;  //"血型"
    private String tel_number;  //"手机号"
    private String email;  //"邮箱"
    private String postal_code;  //"邮编"
    private String home_page;  //"个人主页地址"
    private String household_register_type;  //"户口性质(数据字典：household_register_type)"
//    @JSONField(jsonDirect = true)
    private String birth_place;  //"出生地"
    private String present_address;  //"现住址"
    private String corresponding_address;  //"通讯地址"
    private String family_address;  //"家庭住址"
    private Integer household_registed_id;  //"户籍所在地-省"
    private Integer household_registed_city_id;  //"户籍所在地-市"
    private Integer household_registed_county_id;  //"户籍所在地-旗县"
    private Integer cradkind_id;  //"证件类型"
    private Integer is_onlychild;  //"是否独生子女"
    private Integer is_pre_school_education;  //"是否经过学前教育"
    private Integer is_left_behind_children;  //"是否留守儿童"
    private Integer is_orphan;  //"是否孤儿"
    private Integer is_martyr;  //"是否是烈士或优抚子女"
    private Integer is_study_in_class;  //"是否随班就读"
    private Integer is_government_degree;  //"是否由政府购买学位"
    private Integer is_funding;  //"是否需要申请过资助"
    private Integer is_supplement;  //"是否享受一补"
    private Integer is_move_with;  //"是否迁随子女"

    private String health;  //"健康状态(数据字典：health)"
    private String disabled_status;  //"残疾人类型(数据字典：disabled_status)"
    private LocalDateTime created_at;  //"创建时间"
    private LocalDateTime updated_at;  //"修改时间"
    private LocalDateTime deleted_at;  //"删除标识"


}
