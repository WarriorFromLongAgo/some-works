package com.orhonit.modules.generator.app.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * 学员答题记录表
 * @author YaoSC
 *
 */
@Data
@TableName("os_answer_record")
public class AppOsAnswerRecordEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    //编号
	@Id
	@GeneratedValue(generator= "UUID")
	@TableId
	private String id;
	
	    //试卷编号
	@Column(name = "exam_no")
	private String examNo;
	
	    //考试学员
	@Column(name = "user_id")
	private String userId;
	
	    //问题编号
	@Column(name = "question_no")
	private String questionNo;
	
	    //学员答案
	@Column(name = "user_select")
	private String userSelect;
	
	    //学员答题编号
	@Column(name = "user_no")
	private Integer userNo;
	
	    //创建时间
	@Column(name = "create_time")
	private Date createTime;
	   //更新时间
	@Column(name="update_time")
	private Date updateTime;
	//逻辑删  0:未删  1:已删
	@Column(name="is_del")
	private String isDel;

}
