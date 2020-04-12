package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 	APP端试题管理表
 * @author YaoSC
 *
 */
@Data
@TableName("os_question")
public class AppOsQuestionEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 试题编号
	 */
	@TableId
	private String questionNo;
	/**
	 * 所属题库
	 */
	@Column(name = "library_id")
	private String libraryId;
	/**
	 * 试题题目
	 */
	@Column(name = "question_title")
	private String questionTitle;
	/**
	 * 试题类型
	 */
	@Column(name = "question_type")
	private String questionType;
	/**
	 * 试题答案A
	 */
	@Column(name = "question_a")
	private String questionA;
	/**
	 * 试题答案B
	 */
	@Column(name = "question_b")
	private String questionB;
	/**
	 * 试题答案C
	 */
	@Column(name = "question_c")
	private String questionC;
	/**
	 * 试题答案D
	 */
	@Column(name = "question_d")
	private String questionD;
	/**
	 * 正确答案
	 */
	@Column(name = "answer")
	private String answer;
	/**
	 * 答案解释
	 */
	@Column(name = "answer_desc")
	private String answerDesc;
	/**
	 * 难度级别
	 */
	@Column(name = "level")
	private String level;
    
	/**
	 * 逻辑删   0:未删  1:已删
	 */
	@Column(name="is_del")
	private String isDel;
	//创建时间
	@Column(name="create_time")
	private Date createTime;
	//更新时间
	@Column(name="update_time")
	private Date updateTime;

}
