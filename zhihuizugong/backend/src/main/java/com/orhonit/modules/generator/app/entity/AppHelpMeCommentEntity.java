package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 请您帮帮我    一级评论类
 * Title: AppHelpMeCommentEntity.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月18日
 */
@Data
@TableName("please_help_com_comment")
public class AppHelpMeCommentEntity implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer comId;       //评论ID
	
	private String comContent;  //评论正文
	
	private Date comCreateTime;  //评论时间
	
	private Date comUpdateTime;  //更新时间
	
	private Integer comHelpMeId; //帖子ID
	
	private Integer comUserId; //评论用户ID
	
	
}
