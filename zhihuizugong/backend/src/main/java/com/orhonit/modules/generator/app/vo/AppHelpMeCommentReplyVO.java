package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * Title: 回复
 * @Description:
 * @author YaoSC
 * @date 2019年6月19日 
 */
@Data
public class AppHelpMeCommentReplyVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer replyId;  //评论ID
	
	private Integer userId;   //评论人
	
	private  Integer replyUserId;  //被回复人
	
	private String content;  //回复内容
	
	private Date createTime; //回复时间
	
	private Date updateTime; //更新时间
	
	private  String replyUserTrueName;  //被回复人姓名

}
