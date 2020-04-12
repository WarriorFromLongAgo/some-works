package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;



/**
  * 我为组工出点子  评论回复
 * @author YaoSC
 *
 */
@Data
@TableName("idea_comment_reply")
public class AppIdeaCommentReplyEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 评论回复id
	 */
	@TableId
	private Integer replyId;
	/**
	 * 评论id
	 */
	private Integer ideaCommentId;
	/**
	 * 回复内容
	 */
	private String replyContent;
	/**
	 * 回复人
	 */
	private Long userId;
	/**
	 * 回复谁？
	 */
	private String isReply;
	/**
	 * 回复时间
	 */
	private Date replyTime;


}
