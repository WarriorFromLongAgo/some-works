package com.orhonit.ole.enforce.dto.ps;

import java.util.Date;
import java.util.List;

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
	private String flowType;
	private String isDeal;
	private String isYuj;
	private String isReview;//是否已评查
	
	private String unitName;//当事人单位名称
	private String orgCode;//组织机构代码
	private String legelName;//法定代表人
	private String orgIdCard;//法人身份证号
	
	private String personName;//主执法人员姓名
	private String personNum;
	private String personTel;
	
	private String checkId;
	private String checkTitle;//检查标题
	private String checkNum;//检查编号
	private String checkMode;//检查类型
	private String checkObjectType;//检查对象类型
	private String checkedDate;//检查时间
	private String personId;//主执法人员Id
	private String assistPersonId;//协办人员Id
	private String assistPersonName;//协办人员姓名
	private String stutas;//案件状态
	
	private String userName;
	private String fzContent;
	private String queryDate;
	
	private Integer ricCount;
	private Integer zhuanxCount;
	private Integer anjCount;
	private Integer yujCount;
	private Integer caseCount;
	
	private String epiId;
//	private String zprId;
//	private String zzfryName;
//	private String fzfryName;
//	private String deptIds;
	
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
