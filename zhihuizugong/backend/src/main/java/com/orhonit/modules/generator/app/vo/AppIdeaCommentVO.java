package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;


/**
  * 点子评论共有类
 * @author YaoSC
 *
 */
@Data
public class AppIdeaCommentVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	/**'
	 * 评论人昵称
	 */
	//private String userName;
	/**
	  * 评论id
	 */
	private Integer ideaCommentId;
	
	/**
	  * 评论内容
	 */
	private String ideaCommentContent;
	
	/**
	  * 评论人
	 */
	private Long userId;
	
	/**
	  * 被评论点子id
	 */
	private Integer ideaId;
	
	/**
	  * 评论填写时间
	 */
	private Date createTime;
	
	/**
	 * 评论人昵称
	 */
	private String commentCrtName;
	
	/**
	 * 回复数量
	 */
	private Integer commentReplyCount;
	
	private List<AppIdeaCommentReplyVO> appIdeaCommentReplyVO;  //回复列表
	
	

}
