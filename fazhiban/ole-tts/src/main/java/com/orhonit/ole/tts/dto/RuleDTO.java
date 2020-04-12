package com.orhonit.ole.tts.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author : haoshuai
 * CreateDate : 18-1-8
 * CreateTime : 下午12:39
 */

@Data
public class RuleDTO {
    private Integer ruleId;
    private String ruleName;
    private String execSql;
    private String level;
    private String caseWarnType;
    private Date createDate;
    private String content;
    private List<String> roleIds;
    private String roleId;
    private Integer type;
}
