package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 精细履职主表
 * @author baiam
 *
 */
@TableName("zg_work_plan")
public class WorkPlanEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@TableId
	private String id;
	//用户id
	private Long userId;
	//用户名
	private String userName;
	//工作类型
	private String workType;
	//工作内容
	private String workContent;
	//完成时限
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date workTimeLimit;
	//完成时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date finishTime;
	//1-周计划 2-月安排 3-年谋划 4-领导安排
	private Integer workStatus;
	//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	//所属支部
	private Integer deptId;
	//所属科室
	private Integer lowerId;
	//所属科室名称
	private String lowerName;
	//0未完成 1-已完成 2-需整改 3-曝光
	private Integer exposure;

	
	

	public String getLowerName() {
		return lowerName;
	}

	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getLowerId() {
		return lowerId;
	}

	public void setLowerId(Integer lowerId) {
		this.lowerId = lowerId;
	}

	public Integer getExposure() {
		return exposure;
	}

	public void setExposure(Integer exposure) {
		this.exposure = exposure;
	}

	public Date getWorkTimeLimit() {
		return workTimeLimit;
	}

	public void setWorkTimeLimit(Date workTimeLimit) {
		this.workTimeLimit = workTimeLimit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}
	
}
