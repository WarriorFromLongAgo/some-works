package com.orhonit.ole.server.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EfAttachFile {
    private String id;

    private Date createtime;

    private String createrby;

    private String fileName;

    private String fileNewname;

    private String filePath;

    private String fileTitle;

    private Integer caseId;

    private String docId;

    private String caseStatus;

    private String fileType;

    private String pdfUrl;
}