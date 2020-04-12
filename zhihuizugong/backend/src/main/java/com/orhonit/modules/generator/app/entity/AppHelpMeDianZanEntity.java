package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * Title: 帮帮我  点赞表
 * @Description:
 * @author YaoSC
 * @date 2019年6月19日 
 */
@Data
@TableName("pleas_help_dianzan")
public class AppHelpMeDianZanEntity implements  Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer helpId;         //编号
	
	private Integer helpType;       //标识符: 1:帖子点赞   2：评论点赞  3：回复点赞
	
	private Integer helpUserIdZan;  //点赞用户

}
