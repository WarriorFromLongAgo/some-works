package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CheckDailyDTO {
	private String id;
	
	private String personId;
	
	private String deptId;
	
    private String assistPersonId;
    
	private String checkedUserId;
	
	private String checkNum;
	
	private Integer dealType; //处理方式
	
	private String checkedUserName;
	
	private String checkTitle;
	
	private String checkMode;
	
	private String checkModeId;
	
	private Date checkedDate;
	
	private String roadName;
	
    private String unitName;
    
	private String registNum;
	
	private String legalPerson;
	
	private String checkId;
	
	private String status;
	
	private String checkObjectType;
	
	private String checkSituation;
	
	private String comment; // 核实意见
	
	private String checkZzfryname;
	
	private String checkFzfryname;
	
	private String createName;
	
	private String createBy;
	
	private String flowKey;
	
	private String handleMode;
	
	private String businessId;
	
	private String assignee;
	
	private String nextAssignee;
	
	private int currentPage;//当前页
	private int start;
    private int pageSize;//一页多少条记录
    private int length;
    private int startIndex;//从哪一行开始
    private int endIndex;//从哪一行结束s
    //根据当前所在页数和每页显示记录数计算出startIndex和endIndex
    public void setStartIndexEndIndex(){
        this.startIndex=(this.getCurrentPage()-1)*this.getPageSize();
        this.endIndex= (this.getCurrentPage()-1)*this.getPageSize()+this.getPageSize();
   }
}
