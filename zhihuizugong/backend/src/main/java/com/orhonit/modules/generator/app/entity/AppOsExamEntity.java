package com.orhonit.modules.generator.app.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * APP端试卷管理表
 * @author YaoSC
 *
 */
@Data
@TableName("os_exam")
public class AppOsExamEntity implements Serializable{
      
	private static final long serialVersionUID = 1L;
	
	/**
	 * 试卷编号
	 */
	@TableId
	private String examId;
	/**
	 * 试卷描述
	 */
	private String examDesc;
	/**
	 * 试卷标题
	 */
	private String examTitle;
	/**
	 * 题库编号
	 */
	private String libraryId;
	/**
	 * 题数
	 */
	private Integer questionNumber;
	/**
	 * 单选分值
	 */
	private Integer questionScope;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 实时出分 0:是  1:否
	 */
	private String realResult;
	/**
	 * 是否随机题（0：顺序，1：随机）
	 */
	private String isRandom;
	/**
	 * 出卷人
	 */
	private String createUserId;
	/**
	 * 出卷时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	@Override
	public String toString() {
		return "AppOsExamEntity [examId=" + examId + ", examDesc=" + examDesc + ", examTitle=" + examTitle
				+ ", libraryId=" + libraryId + ", questionNumber=" + questionNumber + ", questionScope=" + questionScope
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", realResult=" + realResult + ", isRandom="
				+ isRandom + ", createUserId=" + createUserId + ", createTime=" + createTime + ", updateTime=" + updateTime+"]";
	}

	
	
}
