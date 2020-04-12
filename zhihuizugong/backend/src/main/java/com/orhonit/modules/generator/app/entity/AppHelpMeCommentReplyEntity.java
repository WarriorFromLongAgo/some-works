package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;


/**
 * Title: 回复评论
 * @Description:
 * @author YaoSC
 * @date 2019年6月19日 
 */
@TableName("please_help_comment_reply")
public class AppHelpMeCommentReplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer replyId;  //评论ID
	
	private Integer userId;   //评论人
	
	private  Integer replyUserId;  //被回复人
	
	private String content;  //回复内容
	
	private Date createTime; //回复时间
	
	private Date updateTime; //更新时间
	
	private  String replyUserTrueName;  //被回复人姓名

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getReplyUserTrueName() {
		return replyUserTrueName;
	}
}
