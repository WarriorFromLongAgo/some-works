package com.orhonit.ole.tts.dto;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class LssuedDetailInfoDTO {
	
	//案件信息
	private String id;
	private String checkTitle;		//检查标题
	private String checkObject;		//检查对象
	private Date startDate;			//检查开始时间
	private Date endDate;			//检查结束时间
	private String status;			//状态
	private String checkBasis;		//检查依据
	private String flowType;
	@Transient
	private String checkContent;	//检查内容
	private String checkWay;		//检查方式 ——字典
	private String checkNum;		//检查编码
	private String createName;		//创建人登录名
	private String createBy;		//创建人名称
	private Date createDate;		//创建检查时间
	private String mglcheckTitle;
	private String mglcheckObject;
	private String mglcheckBasis;
	@Transient
	private String mglcheckContent;
	private String mglcreateName;
	private String deptId;				//执法部门ID
	private String deptName;			//执法部门名称
	private String personId;			//执法人员ID
	private String personName;			//执法人员名称
	@Transient
	private String comment;				
	
	// 被检查信息
	private String recordId;			//Id
	private String assistPersonId;		//协办人
	private String assistPersonName; //协办人姓名
	private String checkDate;          //检查时间
	private String checkMode;			//检查类型
	private String checkObjectType;		//检查对象类型：个人|单位
	private String checkedUserName;		//个人：姓名
	private String checkedUserId;		//个人：身份证号
	private String checkedDate;			//检查时间
	private String roadName;			//道路名称
	private String checkSituation;		//巡查情况
	private String unitName;			//单位名称
	private String registNum;			//注册号
	private String legalPerson;			//法人
}
