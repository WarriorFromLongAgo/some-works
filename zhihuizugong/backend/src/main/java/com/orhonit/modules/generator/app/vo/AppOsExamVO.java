package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 试卷表VO
 * @author YaoSC
 *
 */
@Data
public class AppOsExamVO implements Serializable{
	private static final long serialVersionUID = 1L;

	// 试卷编号
	private String examId;

	// 试卷描述
	private String examDesc;

	// 试卷标题
	private String examTitle;

	// 题库编号
	private String libraryId;

	// 题数
	private Integer questionNumber;

	// 单选分值
	private Integer questionScope;

	// 开始时间
	private Date startTime;

	// 结束时间
	private Date endTime;

	// 实时出分
	private String realResult;

}
