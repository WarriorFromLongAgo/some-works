package com.orhonit.ole.enforce.dto.ps;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleDTO {

	private int id;
	private String title;	//题目
	private String content;	//内容
	private String author;	//作者
	private String area;	//区划
	private String type;	//文章类型
	private String name;	//文章类型名称
	private String createName;
	private String createBy;
	private Date createDate;
	private String updateName;
	private String updateBy;
	private Date updateDate;
	
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
