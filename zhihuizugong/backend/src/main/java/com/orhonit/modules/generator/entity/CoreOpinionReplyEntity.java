package com.orhonit.modules.generator.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 16:10:56
 */
@Data
@TableName("core_opinion_reply")
public class CoreOpinionReplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer replyId;
	/**
	 * 意见ID
	 */
	private String opinionId;
	/**
	 * 回复内容
	 */
	private String replyContent;
	/**
	 * 意见人ID
	 */
	private Long userId;
	/**
	 * 意见人
	 */
	private String userName;
	/**
	 * 回复人ID
	 */
	private Long addId;
	/**
	 * 回复人
	 */
	private String addName;
	/**
	 * 回复时间
	 */
	private String createTime;

}
