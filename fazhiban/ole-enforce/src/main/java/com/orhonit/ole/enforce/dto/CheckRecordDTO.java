package com.orhonit.ole.enforce.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CheckRecordDTO {

	private String id;					// 主键
	private String assistPersonId;		// 协办人
	private String checkMode;			// 检查类型 1：超载 2：超速
	private String checkModeId;			// 检查类型Id
	private String personId;			// 执法人员ID(日常检查的时候使用)
	private String checkObjectType;		// 检查对象类型 1：个人 2：单位
	private String checkedUserName;		// 姓名
	private String checkedUserId;		// 身份证号
	private Date checkedDate;			// 检查时间
	private double lng;					// 纬度
	private double lat;					// 经度
	private String roadName;			// 道路名称
	private String checkSituation;		// 巡查情况
	private String unitName;			// 单位名称
	private String registNum;			// 注册号
	private String legalPerson;			// 法人
	private String createName;			// 创建人名称
	private String createBy;			// 创建人登录名称
	private Date createDate;			// 创建日期
	private String checkId;			// 活动ID
	private String mglCheckedUserName;	// 姓名(蒙文)
	private String mglRoadName;			// 道路名称(蒙文)
	private String mglCheckSituation;	// 巡查情况(蒙文)
	private String mglUnitName;			// 单位名称(蒙文)
	private String mglLegalPerson;		// 法人(蒙文)
	private String mglCreateName;		// 创建人名称(蒙文)
}
