package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;



/**
 * Title: 一级评论列表
 * @Description:
 * @author YaoSC
 * @date 2019年6月18日 
 */
@Data
public class AppHelpMeCommentVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer comId;       //评论ID
	
	private String comContent;  //评论正文
	
	private Date comCreateTime;  //评论时间
	
	private Date comUpdateTime;  //更新时间
	
	private Integer comHelpMeId; //帖子ID
	
	private Integer comUserId; //评论用户ID 
	
	private String commentUserTrueName;   //评论人姓名
	
	private Integer replyTotal; //回复数量
	
	private Integer countThumbsUp;//点赞数量
	
	private String isThumbsUp; //是否点赞  1:已点赞   0:未点赞
	
}
