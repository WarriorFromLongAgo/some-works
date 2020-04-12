package com.orhon.smartcampus.modules.core.graphql.orm.entity;


import lombok.Data;

import javax.persistence.*;


import java.util.Date;


@Data
@Entity
@Table(name = "user_users")
public class User {

    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Integer status;

    @Column(name = "idcard")
    private String idCard;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne( cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    
    
//    
//    @Id
//    private Long id;
//    
//    @Column(name = "email")
//    private String email;  //"邮箱"
//    
//    @Column(name = "username")
//    private String username;  //"用户名"
//    
//    @Column(name = "password")
//    private String password;  //"密码"
//    
//    @Column(name = "status")
//    private Integer status;  //"账号状态"
//    
//    
//    private Integer school_id;  //"学校id"
//    private String idcard;  //"身份证号码"
//    private String last_login_ip;  //"上次登录ip"
//    private String last_login_time;  //"上次登录时间"
//    private String remember_token;
//    private String api_token;  //"api token"
//    private LocalDateTime created_at;  //"创建时间"
//    private LocalDateTime updated_at;  //"更新时间"
//    private LocalDateTime deleted_at;
//    @TableField("user_type")
//    private String user_type;  //"用户类型（admin/teacher/student/other/parent）"
//    private String system_lang;
//    private String mobile;
//    private String equipment_id;
//    private String unionid;


}
