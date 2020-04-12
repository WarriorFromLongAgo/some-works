package com.orhonit.modules.religion.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * 
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@TableName("religion_person_group")
public class ReligionPersonGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @TableId
    private Long id;
	
	    //活动场所id
    private Long religionSiteId;
	
	    //人员id
    private Long religionPersonId;
	
	    //0主要教职人员 1民主管理小组组长 2民主领导小组
    private String type;
	

	public ReligionPersonGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReligionPersonGroup(Long religionSiteId, Long religionPersonId, String type) {
		super();
		this.religionSiteId = religionSiteId;
		this.religionPersonId = religionPersonId;
		this.type = type;
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
	 * 设置：活动场所id
	 */
	public void setReligionSiteId(Long religionSiteId) {
		this.religionSiteId = religionSiteId;
	}
	/**
	 * 获取：活动场所id
	 */
	public Long getReligionSiteId() {
		return religionSiteId;
	}
	/**
	 * 设置：人员id
	 */
	public void setReligionPersonId(Long religionPersonId) {
		this.religionPersonId = religionPersonId;
	}
	/**
	 * 获取：人员id
	 */
	public Long getReligionPersonId() {
		return religionPersonId;
	}
	/**
	 * 设置：0主要教职人员 1民主管理小组组长 2民主领导小组
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：0主要教职人员 1民主管理小组组长 2民主领导小组
	 */
	public String getType() {
		return type;
	}
}
