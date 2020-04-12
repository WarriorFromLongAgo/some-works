package com.orhonit.ole.enforce.dto.ps;

import lombok.Data;

@Data
public class SelectPersonByAreaIdDTO {
	private String id;
	private String name;
	private String sex;
	private String duty;
	private String deptId;
	private String deptName;
	private String lawType;
	private String certNum;
	private String lawArea;
	private String areaName;
	private String picture;
	private String nameMgl;
	private String deptNameMgl;
	
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
