package com.orhonit.ole.tts.dto.ps;

import lombok.Data;

@Data
public class SelectPersonByAreaIdDTO {
	/*姓名		 name
	性别		 sex
	岗位		 duty
	特征类型	 dept_id  关联执法主体表
	证件编码	cert_num
	执法区域	lawarea*/
	private String id;
	private String name;
	private String sex;
	private String duty;
	private String deptId;
	private String lawType;
	private String certNum;
	private String lawArea;
	private String areaName;
	
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
