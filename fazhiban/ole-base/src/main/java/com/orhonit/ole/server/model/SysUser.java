package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUser {
	
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String nickname;

    private String headimgurl;

    private String phone;

    private String telephone;

    private String email;

    private Date birthday;

    private Boolean sex;

    private Boolean status;

    private Date createtime;

    private Date updatetime;

    private String depId;

    private String personId;
}