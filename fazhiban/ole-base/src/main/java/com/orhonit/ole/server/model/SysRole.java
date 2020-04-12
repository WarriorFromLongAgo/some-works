package com.orhonit.ole.server.model;

import java.util.Date;

public class SysRole {
    private Integer id;

    private String name;

    private String description;

    private Date createtime;

    private Date updatetime;

    public SysRole(Integer id, String name, String description, Date createtime, Date updatetime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public SysRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}