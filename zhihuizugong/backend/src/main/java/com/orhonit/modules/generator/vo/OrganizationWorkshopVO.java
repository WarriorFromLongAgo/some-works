package com.orhonit.modules.generator.vo;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class OrganizationWorkshopVO {
	
	private Integer courseId;

	// 课程名称
	@Column(name = "course_name")
	private String courseName;

	// 本地视频（0：不；1：是）
	@Column(name = "is_local")
	private String isLocal;

	// 视频地址
	@Column(name = "course_address")
	private String courseAddress;

	// 封面
	@Column(name = "course_pic")
	private String coursePic;

	// 视频分类
	@Column(name = "course_type")
	private String courseType;

	/**
	 * TODO  暂时不用
	  * 标签  暂时不用
	 */
	@Column(name = "course_label")
	private String courseLabel;

	// 介绍
	@Column(name = "course_desc")
	private String courseDesc;

	// 自选 
	@Column(name = "select_type")
    private String selectType;

	// 推荐（0；一般，1：优选，2：精品，3：高分值，4：置顶）
	@Column(name = "commend_type")
	private String commendType;

    /**
     * TODO  暂时不用
             * 是否允许留言（0：不许；1：允许） 
     */
	@Column(name = "is_message")
	private String isMessage;

	/**
	 * TODO  暂时不用
	  *   时长
	 */
	@Column(name = "course_time")
	private Long courseTime;

	// 积分
	@Column(name = "course_integral")
	private Integer courseIntegral;

	// 发布作者
	@Column(name = "course_author")
	private String courseAuthor;

	// 创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	// 是否发布（0：不；1：是）
	@Column(name = "is_release")
	private String isRelease;

	// 学习人数
	@Column(name = "study_num")
	private Integer studyNum;

	// 截止时间
	@Column(name = "effective_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date effectiveTime;
	
	//姓名
	private String userName;
	//学习状态 ： 1：未学习   2：已学习
	private String studyStatus; 
	

}
