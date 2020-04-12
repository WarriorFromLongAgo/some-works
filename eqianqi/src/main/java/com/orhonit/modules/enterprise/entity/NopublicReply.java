package com.orhonit.modules.enterprise.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * 
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-02 17:26:28
 */
@TableName( "nopublic_reply")
public class NopublicReply implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
	@TableId
    private Long id;
	
	    //企业发布消息id
    private Long nopublicEmId;
	
	    //审核状态  0未审核  1同意  2驳回 
    private String auditStatus;
	
	    //审核意见
    private String auditOpinion;
	
	    //审核用户id
    private Long auditUser;
	
	    //审核时间
    private Timestamp auditCreateTime;
	

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：企业发布消息id
	 */
	public void setNopublicEmId(Long nopublicEmId) {
		this.nopublicEmId = nopublicEmId;
	}
	/**
	 * 获取：企业发布消息id
	 */
	public Long getNopublicEmId() {
		return nopublicEmId;
	}
	/**
	 * 设置：审核状态  0未审核  1同意  2驳回 
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核状态  0未审核  1同意  2驳回 
	 */
	public String getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：审核意见
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	/**
	 * 获取：审核意见
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}
	/**
	 * 设置：审核用户id
	 */
	public void setAuditUser(Long auditUser) {
		this.auditUser = auditUser;
	}
	/**
	 * 获取：审核用户id
	 */
	public Long getAuditUser() {
		return auditUser;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditCreateTime(Timestamp auditCreateTime) {
		this.auditCreateTime = auditCreateTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Timestamp getAuditCreateTime() {
		return auditCreateTime;
	}
}
