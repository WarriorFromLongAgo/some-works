package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EfCase {
    private String id;

    private String caseName;

    private String caseSource;

    private String casAddress;

    private Double lng;

    private Double lat;

    private Date caseApplyDate;

    private String briefCaseContent;

    private String caseHandler;

    private String caseZpr;

    private Date caseZpdate;

    private String caseZzfryid;

    private String caseZzfryname;

    private String caseFzfryid;

    private String caseFzfryname;

    private String caseNum;

    private Date updateDate;

    private String caseType;

    private String depId;

    private String caseReason;

    private Date caseTime;

    private String createName;

    private String createBy;

    private Date createDate;

    private String updateName;

    private String updateBy;

    private String mglBriefCaseContent;

    private String mglCaseFzfryname;

    private String mglCaseZzfryname;

    private String mglCasAddress;

    private String mglCaseName;

    private String mglCreateName;

    private String mglUpdateName;
}