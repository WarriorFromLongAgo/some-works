package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class YujPersonDTO {

	private String id;
	
	private String personId;//预警人员编号
	
	private String personName;//预警人员姓名
	
	private String warnId; //预警编号
	
	private String dealResult; //处理结果
	
	private String isDeal; //是否处理
	
	private String deal; //是否处理
	
	private String isMajor; //处理结果
	
	private String deptId; //部门ID
	
	private String deptName; //部门名称
	
	private Date createDate; //创建时间
	
	private String createName; //创建人姓名
	
	private String createBy;  //创建人证件编号
	
	private Date updateDate; //修改时间
	
	private String updateName; //修改人姓名	
	
	private String updateBy; // 修改人证件编号
}
