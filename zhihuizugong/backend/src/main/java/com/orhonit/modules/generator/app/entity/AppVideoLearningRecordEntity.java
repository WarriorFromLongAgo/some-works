package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;


/**
  * 组工讲堂  学习记录
 * @author YaoSC
 *
 */
//@Data
@TableName("os_course_record")
public class AppVideoLearningRecordEntity implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	//学习记录
	private Integer recordId;
	//用户
	private Integer userId;
	//视频编号
	private Integer courseId; 
	//学习时长
	private Integer rememberTime;
	//获得积分
	private Double getIntegral;
	//学习时间
	private Date createTime;
	//更新时间
	private  Date updateTime;
	//标识符: :组工讲堂   4:组工书苑
	private  String identifier; 
	//学习状态    1:未学习    2:已学习
	private  String studyStatus;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getRememberTime() {
		return rememberTime;
	}
	public void setRememberTime(Integer rememberTime) {
		this.rememberTime = rememberTime;
	}
	
	public Double getGetIntegral() {
		return getIntegral;
	}
	public void setGetIntegral(Double getIntegral) {
		this.getIntegral = getIntegral;
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
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getStudyStatus() {
		return studyStatus;
	}
	public void setStudyStatus(String studyStatus) {
		this.studyStatus = studyStatus;
	}
	
	

}
