package com.orhonit.ole.tts.dto.api;

import lombok.Data;
@Data
public class ApiYujDTO {
	
	private String id;
	private String level;
	private String personId;
	private String content;
	private String createDate;
	private String recordId;
	private String recordCode;
	private String deptId;
	private String deptName;
	private String areaId;
	private String areaName;
	private String type;
	private String taskId;
	private String deal;
	private String dealResult;
	private String warnType;
	
	private String yujCount;
	
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
