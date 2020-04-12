package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * APP端考答案正确表
 * @author YaoSC
 *
 */
@Data
@TableName("os_exam_question")
public class AppOsExamQuestionEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	// id
	//@Id
	//@GeneratedValue(generator = "UUID")
	@TableId
	private String id;

	// 卷子编号
	@Column(name = "paper_no")
	private String paperNo;

	// 学员id
	@Column(name = "user_id")
	private String userId;
	
	// 试题编号
	@Column(name = "question_no")
	private String questionNo;
	
	// 正确答案
	@Column(name = "question_answer")
	private String questionAnswer;
	
	//答题类型（1:顺序，2：智能刷题，3：高频错题，4：套卷练习，5：在线考试）
	@Column(name ="type")
	private String type;
	//创建时间
	@Column(name="create_time")
	private Date createTime;
	//更新时间
	@Column(name="update_time")
	private Date updateTime;
	
	//逻辑删   0:未删  1:已删
	@Column(name="is_del")
	private String isDel;
}
