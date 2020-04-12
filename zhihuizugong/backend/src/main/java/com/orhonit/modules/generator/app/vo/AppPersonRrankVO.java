package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
  *  个人答题统计
 * @author YaoSC
 *
 */
@Data
public class AppPersonRrankVO implements Serializable{
	private static final long serialVersionUID = 1L;

	// 排名
	private Integer index;

	// 用户姓名
	private String name;

	// 答题次数
	private Integer answerCount;

	// 平均用时
	private String avgTime;

	// 正确数
	private Integer yesCount;

	// 错误数
	private Integer errorCount;

	// 正确率
	private Integer correctRate;

	// 最后答题时间
	private Date answerTime;

	// 发布时间
	private Date updateTime;
	// 总分
	private Integer sumScore;

}
