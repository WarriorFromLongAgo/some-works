package com.orhonit.modules.enterprise.model;

import java.util.List;

/**
 * 用户返回企业或民宗局发布的信息集合
 * 
 * @author cyf
 * @date 2018/11/05 下午7:57:29
 */
public class NopublicEnterpriseMsgModel {

	private Long id;

	// 标题
	private String title;

	// 内容
	private String content;

	// 发布用户名称
	private String userName;

	// 接收企业id
	private Long receiveEnterpriseId;

	// 接收企业名称
	private String receiveEnterpriseName;

	// 0否 1是
	private String isRead;

	private String auditStatus;// 审批状态

	// 附件
	private List<NopublicFileModel> accessoryIds;
	

	public Long getReceiveEnterpriseId() {
		return receiveEnterpriseId;
	}

	public void setReceiveEnterpriseId(Long receiveEnterpriseId) {
		this.receiveEnterpriseId = receiveEnterpriseId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiveEnterpriseName() {
		return receiveEnterpriseName;
	}

	public void setReceiveEnterpriseName(String receiveEnterpriseName) {
		this.receiveEnterpriseName = receiveEnterpriseName;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public List<NopublicFileModel> getAccessoryIds() {
		return accessoryIds;
	}

	public void setAccessoryIds(List<NopublicFileModel> accessoryIds) {
		this.accessoryIds = accessoryIds;
	}

	public NopublicEnterpriseMsgModel(Long id, String title, String content, String userName,
			Long receiveEnterpriseId,String receiveEnterpriseName, String isRead, List<NopublicFileModel> accessoryIds, String auditStatus) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.userName = userName;
		this.receiveEnterpriseId=receiveEnterpriseId;
		this.receiveEnterpriseName = receiveEnterpriseName;
		this.isRead = isRead;
		this.accessoryIds = accessoryIds;
		this.auditStatus = auditStatus;
	}

	public NopublicEnterpriseMsgModel() {
	}

}
