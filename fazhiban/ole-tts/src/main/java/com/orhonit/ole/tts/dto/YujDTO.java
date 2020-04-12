package com.orhonit.ole.tts.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 预警
 */

@Data
public class YujDTO {

	private String id;
	
	private String star; //预警星级
	
	private String level; //预警级别:1 蓝色  2 黄色  3 红色
	
	private String content; //预警内容
	
	private String recordTitle; //案件名称
	
	private String recordId; //案件ID
	
	private String recordCode; //案件编号
	
	private String type; //预警指标: 1 不予立案  2 不予处理....
	
	private String taskId; //定时任务编号
	
	private String warnType; //预警类型：实时预警，时限预警，过程预警等
	
	private Date createDate; //创建时间
	
	private String createName; //创建人姓名
	
	private String createBy;  //创建人证件编号
	
	private Date updateDate; //修改时间
	
	private String updateName; //修改人姓名	
	
	private String updateBy; // 修改人证件编号
	
	//new add by liuzh
	
	private String recordStatus; //案件状态
	
	private String flowType; //流程类型
	
	private String deptId; //部门ID
	
	private String isDeal; //是否处理
	
	private String deptName; //部门名称
	
	private String personName; //被预警人名称
		
	private List<YujPersonDTO> yujPersons;  //预警人员
	
	private List<YujPersonCountDTO> yujPersonCount;
    
}
