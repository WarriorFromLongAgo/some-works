package com.orhonit.modules.religion.model;


import java.sql.Timestamp;
import java.util.List;

import com.orhonit.modules.religion.entity.ReligionPerson;

import lombok.Data;


/**
 * 宗教场所 用于返回前台表单
 * 
 * @author cyf
 * @date 2018/11/12 下午4:11:42
 */
@Data
public class ReligionSiteModel {

	// 主键
	private Long id;

	// 省
	private String shen;

	// 市
	private String shi;

	// 县
	private String xian;

	// 类型
	private String type;

	// 场所名称
	private String name;

	// 地址
	private String address;

	// 所属嘎查
	private String gachaId;
	
	private String gachaName;

	// 0寺观教堂 1固定处所
	private String siteType;

	// 批准设立机关
	private String ratifyEstablishOffice;

	// 批准设立机关时间
	private Timestamp ratifyEstablishTime;

	// 登记机关
	private String registerOffice;

	// 登记时间
	private Timestamp registerTime;

	// 登记id
	private String registerId;
	
//	private List<ReligionPerson> primaryPerson;//主要教职人员 json格式字符串
//	
//	private List<ReligionPerson> goupPerson;//民主管理小组组长
//	
//	private List<ReligionPerson> leadGroup;//民主领导小组
	
	private String primaryPersonIds;//主要教职人员 id
	private String goupPersonIds;//民主管理小组组长id
	private String leadGroupIds;//民主领导小组id
	

	// 场所联系电话
	private String phone;

	// 场所建筑面积
	private Double area;

	// 占地面积
	private Double coverAnArea;

	// 常住人数
	private Integer residentNum;

	// 文物级别
	private String relicLvl;

	// 备案
	private String putOnRecords;

	// 寺庙简介
	private String about;

	// 附件ids ,号分隔
	private String fileIds;


}
