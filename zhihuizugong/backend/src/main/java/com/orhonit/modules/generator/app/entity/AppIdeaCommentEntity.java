package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
  * 我为组工出点子 评论
 * @author YaoSC
 *
 */
@Data
@TableName("idea_comment")
public class AppIdeaCommentEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	  * 评论id
	 */
	@Column(name = "idea_comment_id")
	private Integer ideaCommentId;
	
	/**
	  * 评论内容
	 */
	@Column(name = "idea_comment_content")
	private String ideaCommentContent;
	
	/**
	  * 评论人
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	  * 被评论点子id
	 */
	@Column(name = "idea_id")
	private Integer ideaId;
	
	/**
	  * 评论填写时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 回复数量
	 */
	@Column(name = "comment_reply_count")
	private Integer commentReplyCount;
	
	


}
