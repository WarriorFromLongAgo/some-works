package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePotence {
    private String id;

    private String code;

    private String name;

    private String parentId;

    private String processId;

    private String duty;

    private String dutyRef;

    private String accBasis;

    private String limitTime;

    private String remarks;

    private String isEffect;

    private String delFlag;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private Date updateDate;

    private String mglName;

    private String mglRemarks;

    private String mglDuty;

    private String mglDutyRef;

    private String mglAccBasis;

    private String mglCreateName;

    private String mglUpdateName;
}