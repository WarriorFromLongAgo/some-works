package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * 我为组工出点子
 * @author YaoSC
 *
 */
@Data
@TableName("idea_team_up_ideas")
public class TeamUpIdeaEntity implements Serializable{
private static final long serialVersionUID = 1L;
	
	//出点子ID
    @TableId
	@Column(name = "idea_id")
	private Integer ideaId;
	
	//点子标题
	@Column(name = "idea_title")
	private String ideaTitle;
	
	//点子内容
	@Column(name = "idea_content")
	private String ideaContent;
	
	//创建时间
	@Column(name = "idea_create_time")
	private Date ideaCreateTime; 
	
	//创建人
	@Column(name = "create_user_id")
	private Integer createUserId;
	
	/*
	 * //点子点击量
	 * 
	 * @Column(name = "idea_click") private Integer ideaClick;
	 */
	
	//点赞数量
	@Column(name="idea_zan")
	private Integer ideaZan;
}
