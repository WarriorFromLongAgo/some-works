package com.orhonit.ole.tts.dto;

import lombok.Data;

/**
 * 预警
 */

@Data
public class WarnDTO {

    private String id;

    private String level;		//预警级别

    private String content;		//预警内容

    private String createDate;	//创建时间
    
    private String recordTitle;	//案件标题

    private String recordId;	//单据主键

    private String recordCode;	//单据编号
    
    private String recordStatus;	//记录状态
    
    private String flowType;	//流程类型

    private String type;		//预警指标

    private String taskId;		//定时任务编号

    private String warnType;	//预警类型
    
    private String star;	//星级
    
    private String createName;	//创建人名称
    
    private String createBy;	//创建人登录名称
    
    private String updateName;	//更新人名称
    
    private String updateBy;	//更新人登录名称
    
    private String updateDate;	//更新日期
}
