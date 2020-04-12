package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePotenceLawMap {
    private String id;

    private String rightDutyId;

    private String wzCatalogId;

    private String fzCatalogId;

    private String isEffect;

    private String delFlag;

    private String lang;

    private String createBy;

    private Date createDate;

    private String createName;

    private String updateBy;

    private String updateName;

    private Date updateDate;

    private String mglCreateName;

    private String mglUpdateName;

}