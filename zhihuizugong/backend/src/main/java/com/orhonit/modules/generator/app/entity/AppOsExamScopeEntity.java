package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 答题成绩表
 * @author YaoSC
 *
 */
@TableName("os_exam_scope")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppOsExamScopeEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	// id
	@Id
	@GeneratedValue(generator="UUID")
	private String id;

	// 试卷编号
	@Column(name = "paper_no")
	private String paperNo;

	// 学员id
	@Column(name = "user_id")
	private String userId;

	// 试卷成绩
	@Column(name = "paper_score")
	private Integer paperScore;

	// 正确数
	@Column(name = "yes")
	private Integer yes;

	// 错误数
	@Column(name = "error")
	private Integer error;

	// 答题时间
	@Column(name = "answer_time")
	private Date answerTime;

	// 答题用时
	@Column(name = "use_time")
	private String useTime;
	
	// 成绩来源（1:练习，2：正式考试）
	@Column(name ="type")
	private String type;
    //创建时间
	@Column(name="create_time")
	private Date createTime;
	
	//更新时间
	@Column(name="update_time")
	private  Date updateTime;
	//逻辑删  0:未删  1:已删
	@Column(name="is_del")
	private  String isDel;
}
