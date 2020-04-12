package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 问题表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 09:34:14
 */
@Data
@TableName("os_question")
public class OsQuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 问题编号
	 */
	@TableId
	private String questionNo;
	/**
	 * 所属题库
	 */
	private String libraryId;
	/**
	 * 问题题目
	 */
	private String questionTitle;
	/**
	 * 问题类型
	 */
	private String questionType;
	/**
	 * 问题答案A
	 */
	private String questionA;
	/**
	 * 问题答案B
	 */
	private String questionB;
	/**
	 * 问题答案C
	 */
	private String questionC;
	/**
	 * 问题答案D
	 */
	private String questionD;
	/**
	 * 正确答案
	 */
	private String answer;
	/**
	 * 答案解释
	 */
	private String answerDesc;
	/**
	 * 难度级别
	 */
	private String level;
	
	/**
	 * 逻辑删除  0:未删除    1:已删除
	 */
	private String isDel;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
