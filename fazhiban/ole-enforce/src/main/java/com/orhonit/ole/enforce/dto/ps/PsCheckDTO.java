package com.orhonit.ole.enforce.dto.ps;

import lombok.Data;

@Data
public class PsCheckDTO {
	
	private String areaId;
	private String areaName;
	
	private String deptId;
	private String deptName;
	
	private String checkId;
	private String checkTitle;//检查标题
	private String checkNum;//检查编号
	private String checkMode;//检查类型
	private String checkModeId;//检查类型ID
	private String checkMode1;//检查类型
	private String checkObjectType;//检查对象类型
	private String checkedDate;//检查时间
	private String personId;//主执法人员Id
	private String personName;//主执法人员姓名
	private String assistPersonId;//协办人员Id
	private String assistPersonName;//协办人员姓名
	private String checkStutas;//案件状态
	private String roadName;
	
	private String checkObjeck;//检查对象
	private String startDate;//检查开始时间
	private String endDate;//检查结束时间
	private String checkBasis;//检查依据
	private String checkContent;
	private String checkWay;
	private String createBy;
	private String createName;
	private String registName;
	private String legalPerson;
	private String unitName;
	
	private String queryDate;
	
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
