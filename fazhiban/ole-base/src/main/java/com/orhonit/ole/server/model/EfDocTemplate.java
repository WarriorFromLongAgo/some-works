package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EfDocTemplate {
    private String id;

    private String name;

    private String isEffect;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private Date updateDate;

    private String code;

    private String content;
}