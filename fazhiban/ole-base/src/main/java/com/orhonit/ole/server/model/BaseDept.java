package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDept {
    private String code;

    private String nameSpell;

    private String id;

    private String address;

    private String legalPerson;

    private Integer level;

    private Integer deptProperty;

    private String parentId;

    private Integer sort;

    private String lawType;

    private String tel;

    private String areaId;

    private String lawarea;

    private String ifEffect;

    private String delFlag;

    private String createBy;

    private String createName;

    private Date createDate;

    private String updateBy;

    private String updateName;

    private Date updateDate;

    private String name;

    private String mglName;

    private String mglAddress;

    private String mglLegalPerson;

    private String mglCreateName;

    private String mglUpdateName;

}