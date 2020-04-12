package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawLevel {
	
    private String id;

    private String name;

    private String parentId;
    
    private String parentName;

    private String nameEn;

    private Integer sort;

    private String ifEffect;

    private String delFlag;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String createName;

    private String updateName;

    private String mglName;
    
    private String mglPanrentName;

    private String mglCreateName;

    private String mglUpdateName;
    
    private String mglIsEffect;

}