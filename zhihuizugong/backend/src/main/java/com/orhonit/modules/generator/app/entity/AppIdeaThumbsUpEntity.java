package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
  * 为组工出点子 点赞
 * @author YaoSC
 *
 */
@Data
@TableName("idea_thumbs_up")
public class AppIdeaThumbsUpEntity implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	
	/**
	  * 用户id
	 */
	private Long userId;
	/**
	  * 编号
	 */
	private Integer ideaId;
	
	
	private String ideaType;   //标识符  1:帖子点赞  2:评论点赞  3:回复点赞
 

}
