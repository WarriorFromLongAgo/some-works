package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *   错题记录表
 * @author YaoSC
 *
 */
@Data
@TableName("os_error_question")
@NoArgsConstructor
public class AppOsErrorQuestionEntity  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	    //错题编号
	@Id
	@GeneratedValue(generator= "UUID")
	private String id;
	
	    //问题编号
	@Column(name = "question_no")
	private String questionNo;
	
	    //错误选项
	@Column(name = "error_answer")
	private String errorAnswer;
	
	    //用户id
	@Column(name = "user_id")
	private String userId;
	
	    //创建时间
	@Column(name = "create_time")
	private Date createTime;
	
	//逻辑删除   0:未删  1:已删
	@Column(name="is_del")
	private  String isDel;
	//更新时间
	@Column(name="update_time")
	private Date updateTime;
	
	@Transient
	private Integer count;
	
	public AppOsErrorQuestionEntity(String id, String questionNo, String errorAnswer, String userId, Date createTime,Date updateTime) {
		super();
		this.id = id;
		this.questionNo = questionNo;
		this.errorAnswer = errorAnswer;
		this.userId = userId;
		this.createTime = createTime;
		this.updateTime=updateTime;
	}
}
