package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLawCategory {
    private String id;

    private String code;

    private String name;

    private String nameEn;

    private Date updateDate;

    private String effect;

    private Date publishDate;

    private Integer version;

    private String effectLevel;

    private String pubDept;

    private String itemType;

    private Date actDate;

    private Date effectDate;

    private Integer useTimes;

    private String sourceHref;

    private String isEffect;

    private String delFlag;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private String mglName;

    private String mglCreateName;

    private String mglUpdateName;

    
}