package com.orhonit.modules.generator.app.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;



/**
 * 大家来分享
 * @author YaoSC
 *
 */
//@Data
@TableName("everybody_share")
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppEverybodyShareEntity implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分享ID
	 */
	@TableId
	private Integer shareId;
	
	/**
	 * 分享标题
	 */
	private String shareTitle;
	
	/**
	 * 分享内容
	 */
	private String shareContent;
	
	/**
	 * 保存路径
	 */
	private String shareUrl;
	
	/**
	 * 分享时间
	 */
	private Date shareCreateTime;
	
	/**
	 * 分享人
	 */
	private Long shareUserId;
	
	/**
	 * 上传类型:   1:图片 2:视频 3:其他
	 */
	private Integer shareType;
	
	private Integer lowerId;  //科室ID
	
	private String lowerName;  //科室名称
	
	/**
	 * 真实姓名
	 */
	@Transient
	private String userTrueName;

	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public Date getShareCreateTime() {
		return shareCreateTime;
	}

	public void setShareCreateTime(Date shareCreateTime) {
		this.shareCreateTime = shareCreateTime;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
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

	public String getUserTrueName() {
		return userTrueName;
	}
	

}
