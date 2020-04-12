package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
  * 点子  评论回复共有类
 * @author YaoSC
 *
 */
@Data
public class AppIdeaCommentReplyVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 回复内容
	 */
    private String replyContent;
	/**
	 * 回复人ID
	 */
	private int userId;
	
	/**
	 * 回复人名字
	 */
	private String userName;
	
	/**
	 * 回复给谁
	 */
	private String isReply;
	
	/**
	 * 回复时间
	 */
	private Date replyTime;
	
	/**
	 * 回复数量
	 */
	private Integer commentReplyCount;

}
