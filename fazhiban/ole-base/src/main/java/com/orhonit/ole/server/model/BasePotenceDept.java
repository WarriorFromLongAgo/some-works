package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePotenceDept {
    private String id;

    private String potenceId;

    private String deptId;

    private String deptIdAgent;

    private String isEffect;

    private String delFlag;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private Date updateDate;

    private String mglCreateName;

    private String mglUpdateName;
}