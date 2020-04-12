package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EfPartyInfo {
    private String id;

    private String name;

    private String postCode;

    private String idCard;

    private String sex;

    private Integer age;

    private String tel;

    private String address;

    private String unitName;

    private String legalName;

    private String orgIdCard;

    private String orgCode;

    private String type;

    private Integer caseId;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private Date updateDate;

    private String mglName;

    private String mglAddress;

    private String mglUnitName;

    private String mglLegalName;

    private String mglCreateName;

    private String mglUpdateName;

    private String unitAddress;

    private String mglUnitAddress;
}