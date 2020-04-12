package com.orhonit.modules.enterprise.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Transient;

/**
 * 企业或民宗局消息
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-02 17:26:29
 */
@TableName( "nopublic_enterprise_msg")
public class NopublicEnterpriseMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
	@TableId
    private Long id;
	
	    //0企业  1民宗局
    private String type;
	
	    //标题
    private String title;
	
	    //内容
    private String content;
	
	    //附件id,号分隔
    private String accessoryIds;
	
	    //发布企业id
    private Long userId;
    
    @Transient
    private String userName;
	
	    //接收企业id
    private Long receiveEnterpriseId;
    
    @Transient
    private String receiveEnterpriseName;
    
    @Transient
    private String auditStatus; //审批状态
	
	    //0否  1是
    private String isRead;

	    //创建用户
    private Long createUser;
	
	    //创建时间
    private Timestamp createTime;
	
    
    

	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReceiveEnterpriseName() {
		return receiveEnterpriseName;
	}
	public void setReceiveEnterpriseName(String receiveEnterpriseName) {
		this.receiveEnterpriseName = receiveEnterpriseName;
	}
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
	 * 设置：0企业  1民宗局
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：0企业  1民宗局
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：附件id    ,号分隔
	 */
	public void setAccessoryIds(String accessoryIds) {
		this.accessoryIds = accessoryIds;
	}
	/**
	 * 获取：附件id    ,号分隔
	 */
	public String getAccessoryIds() {
		return accessoryIds;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 设置：接收企业id
	 */
	public void setReceiveEnterpriseId(Long receiveEnterpriseId) {
		this.receiveEnterpriseId = receiveEnterpriseId;
	}
	/**
	 * 获取：接收企业id
	 */
	public Long getReceiveEnterpriseId() {
		return receiveEnterpriseId;
	}
	/**
	 * 设置：0否  1是
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	/**
	 * 获取：0否  1是
	 */
	public String getIsRead() {
		return isRead;
	}
	/**
	 * 设置：创建用户
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建用户
	 */
	public Long getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
}
