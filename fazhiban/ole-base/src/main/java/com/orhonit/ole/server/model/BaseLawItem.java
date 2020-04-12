package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLawItem {
    private String id;

    private String itemCode;

    private String lawId;

    private String parentId;

    private String itemName;

    private String itemDes;

    private String itemExplain;

    private String remarks;

    private String delFlag;

    private String version;

    private Date publishDate;

    private Date effectDate;

    private Integer useTimes;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String createName;

    private String updateName;

    private String mglItemName;

    private String mglItemDes;

    private String mglItemExplain;

    private String mglRemarks;

    private String mglCreateName;

    private String mglUpdateName;

}