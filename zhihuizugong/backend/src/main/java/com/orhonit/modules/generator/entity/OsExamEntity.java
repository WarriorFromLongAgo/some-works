package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 试卷表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-07 18:11:19
 */
@Data
@TableName("os_exam")
public class OsExamEntity implements Serializable {
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
	 * 实时出分
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
	
	/**
	 * 逻辑删除   0:未删  1:已删除
	 */
	private String isDel ;

}
