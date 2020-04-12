package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;

import com.orhonit.modules.generator.app.entity.ApphelpMeEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;




/**
 * Title: 请您帮帮我  
 * @Description: 帖子共有类
 * @author YaoSC
 * @date 2019年6月18日 
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class AppHelpMeVO extends ApphelpMeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userTrueName;          //发布人姓名
	
	private Integer helpThumbsUp;         //点赞数量
	
	private Integer  commentTotal;        //评论总数
	
	private String helpZan;               //是否点赞 1:是  0:否
	
	//private List<AppHelpMeCommentVO>commentList; //所有评论
	
	//private List<AppHelpMeCommentReplyVO>commentReplyList;//所有回复

}
