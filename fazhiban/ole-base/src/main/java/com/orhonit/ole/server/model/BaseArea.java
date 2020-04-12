package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseArea {
	
    private String id;

    private String code;

    private String name;

    private String parentId;

    private String nameEn;

    private String sort;

    private String isEffect;

    private String delFlag;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String createName;

    private String updateName;

    private String mglName;

    private String mglCreateName;

    private String mglUpdateName;

}