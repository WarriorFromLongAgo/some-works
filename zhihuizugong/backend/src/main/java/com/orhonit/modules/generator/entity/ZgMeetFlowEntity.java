package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 会议流程
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-17 10:32:02
 */
@Data
@TableName("zg_meet_flow")
public class ZgMeetFlowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 创建人id
	 */
	private Long createUserId;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 会议id
	 */
	private String meetId;
	/**
	 * 会议标题
	 */
	private String meetTitle;
	/**
	 * 制定方案人id
	 */
	private Long makePlan;
	/**
	 * 制发通知人id
	 */
	private Long makeInform;
	/**
	 * 准备材料人id
	 */
	private Long readyMaterial;
	/**
	 * 会场布置人id
	 */
	private Long meetRoomDecorate;
	/**
	 * 会议报道人id
	 */
	private Long meetReports;
	/**
	 * 会场服务人id
	 */
	private Long meetServe;
	/**
	 * 会议记录人id
	 */
	private Long meetRecord;
	/**
	 * 后勤保障人id
	 */
	private Long logistics;
	/**
	 * 整理资料人id
	 */
	private Long sortingData;
	/**
	 * 会议纪要人id
	 */
	private Long meetMinutes;
	/**
	 * 督查落实人id
	 */
	private Long inspectorWorkable;
	/**
	 * 会后反馈人id
	 */
	private Long meetFeedback;
	/**
	 *
	 */
	private String makePlanName;
	/**
	 *
	 */
	private String makeInformName;
	/**
	 *
	 */
	private String readyMaterialName;
	/**
	 *
	 */
	private String meetRoomDecorateName;
	/**
	 *
	 */
	private String meetReportsName;
	/**
	 *
	 */
	private String meetServeName;
	/**
	 *
	 */
	private String meetRecordName;
	/**
	 *
	 */
	private String logisticsName;
	/**
	 *
	 */
	private String sortingDataName;
	/**
	 *
	 */
	private String meetMinutesName;
	/**
	 *
	 */
	private String inspectorWorkableName;
	/**
	 *
	 */
	private String meetFeedbackName;

}
