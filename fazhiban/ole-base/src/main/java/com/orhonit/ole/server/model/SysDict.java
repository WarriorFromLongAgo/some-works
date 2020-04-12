package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysDict  {
    private String typeDesc;

    private String enumDesc;

    private Date createdDate;

    private Date lastUpdate;

    private String lang;

    private Integer sort;
}