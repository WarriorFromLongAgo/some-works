package com.orhonit.ole.tts.dto.ps;

import java.util.Date;
import lombok.Data;
@Data
public class PsCaseDTO {
	
	private String name;
	// 案件
	private String caseId;
	private String caseName;
	private String caseSource;
	private String caseAddress;
	private Date caseApplyDate;
	private String briefCaseContent;
	private String caseHandler;
	private String caseZpr;
	private Date caseZpdate;
	private String caseZzfryname;
	private String caseFzfryname;
	private String caseZzfryid;
	private String caseFzfryid;
	private String caseStatus;
	private String caseNum;
	private String caseUpdateDate;
	private String caseType;
	private Date caseTime;
	private String caseReason;
	private String areaId;
	private String areaName;
	private String deptId;
	private String deptName;
	
	private String unitName;
	private String orgCode;
	private String legelName;
	private String orgIdCard;
	
	private int currentPage;//当前页
    private int pageSize;//一页多少条记录
    private int startIndex;//从哪一行开始
    private int endIndex;//从哪一行结束s
    //根据当前所在页数和每页显示记录数计算出startIndex和endIndex
    public void setStartIndexEndIndex(){
        this.startIndex=(this.getCurrentPage()-1)*this.getPageSize();
        this.endIndex= (this.getCurrentPage()-1)*this.getPageSize()+this.getPageSize();
   }
}
