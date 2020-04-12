package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysPermission {
    private Integer id;

    private Integer parentid;

    private String name;

    private String css;

    private String href;

    private Boolean type;

    private String permission;

    private Integer sort;
}