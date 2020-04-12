package com.orhon.smartcampus.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.HashMap;

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
@TableName("user_users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;


    private String email;  //"邮箱"
    private String username;  //"用户名"
    private String password;  //"密码"
    private Integer status;  //"账号状态"
    private Integer school_id;  //"学校id"
    private String idcard;  //"身份证号码"
    private String last_login_ip;  //"上次登录ip"
    private String last_login_time;  //"上次登录时间"
    private String remember_token;
    private String api_token;  //"api token"
    private LocalDateTime created_at;  //"创建时间"
    private LocalDateTime updated_at;  //"更新时间"
    private LocalDateTime deleted_at;
    @TableField("user_type")
    private String user_type;  //"用户类型（admin/teacher/student/other/parent）"
    private String system_lang;
    private String mobile;
    private String equipment_id;
    private String unionid;
}
