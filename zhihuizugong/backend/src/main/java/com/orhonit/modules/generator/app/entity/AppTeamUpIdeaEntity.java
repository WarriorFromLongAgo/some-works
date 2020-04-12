package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * 我为组工出点子
 * @author YaoSC
 *
 */
//@Data
@TableName("idea_team_up_ideas")
public class AppTeamUpIdeaEntity implements Serializable{
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
	
	//评论数量
	private Integer ideaClick;
	
	//点赞数量
	@Column(name="idea_zan")
	private Integer ideaZan;
	
	
	private Integer lowerId; //科室ID
	
	private String lowerName;//科室名称
	@JSONField(jsonDirect=true)
	@Column(name="idea_route")
	private String ideaRoute; //路径:{type:类型 1:图片 2:视频  3:其他  path:路径}
	//发布人姓名
	private String userTrueName;
	
	private String isZan;   //是否点赞    1:已点赞 0：未点赞

	public Integer getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Integer ideaId) {
		this.ideaId = ideaId;
	}

	public String getIdeaTitle() {
		return ideaTitle;
	}

	public void setIdeaTitle(String ideaTitle) {
		this.ideaTitle = ideaTitle;
	}

	public String getIdeaContent() {
		return ideaContent;
	}

	public void setIdeaContent(String ideaContent) {
		this.ideaContent = ideaContent;
	}

	public Date getIdeaCreateTime() {
		return ideaCreateTime;
	}

	public void setIdeaCreateTime(Date ideaCreateTime) {
		this.ideaCreateTime = ideaCreateTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getIdeaClick() {
		return ideaClick;
	}

	public void setIdeaClick(Integer ideaClick) {
		this.ideaClick = ideaClick;
	}

	public Integer getIdeaZan() {
		return ideaZan;
	}

	public void setIdeaZan(Integer ideaZan) {
		this.ideaZan = ideaZan;
	}

	public String getUserTrueName() {
		return userTrueName;
	}

	public Integer getLowerId() {
		return lowerId;
	}

	public void setLowerId(Integer lowerId) {
		this.lowerId = lowerId;
	}

	public String getLowerName() {
		return lowerName;
	}

	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}

	public String getIsZan() {
		return isZan;
	}

	public void setIsZan(String isZan) {
		this.isZan = isZan;
	}

	public String getIdeaRoute() {
		return ideaRoute;
	}

	public void setIdeaRoute(String ideaRoute) {
		this.ideaRoute = ideaRoute;
	}
	
	
	
	
}
