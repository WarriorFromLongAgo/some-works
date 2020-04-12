package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EfDocContent {
    private String id;

    private String caseId;

    private String templateId;

    private String partyId;

    private String value;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private Date updateDate;
}