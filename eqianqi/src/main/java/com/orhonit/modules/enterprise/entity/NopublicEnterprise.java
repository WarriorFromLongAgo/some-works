package com.orhonit.modules.enterprise.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * 非公经济企业表
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-02 17:26:29
 */
@TableName("nopublic_enterprise")
public class NopublicEnterprise implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	    //主键
    @TableId
    private Long id;
	
	    //企业名称
    private String name;
	
	    //企业法人
    private String legalPerson;
	
	    //注册时间
    private Timestamp registerTime;
	
	    //注册资金
    private String registerMoney;
	
	    //服务类型
    private String serviceType;
	
	    //企业地址
    private String enterpriseAddress;
	
	    //企业简介
    private String enterpriseIntro;
	
	    //企业备注
    private String remark;
	
	    //创建用户
    private Long createUser;
	
	    //创建时间
    private Timestamp createTime;
	
    
    

    
    
	public NopublicEnterprise() {
	}
	public NopublicEnterprise(Long id) {
		this.id = id;
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
	 * 设置：企业名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：企业名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：企业法人
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	/**
	 * 获取：企业法人
	 */
	public String getLegalPerson() {
		return legalPerson;
	}
	/**
	 * 设置：注册时间
	 */
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * 获取：注册时间
	 */
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	/**
	 * 设置：注册资金
	 */
	public void setRegisterMoney(String registerMoney) {
		this.registerMoney = registerMoney;
	}
	/**
	 * 获取：注册资金
	 */
	public String getRegisterMoney() {
		return registerMoney;
	}
	/**
	 * 设置：服务类型
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * 获取：服务类型
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * 设置：企业地址
	 */
	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}
	/**
	 * 获取：企业地址
	 */
	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}
	/**
	 * 设置：企业简介
	 */
	public void setEnterpriseIntro(String enterpriseIntro) {
		this.enterpriseIntro = enterpriseIntro;
	}
	/**
	 * 获取：企业简介
	 */
	public String getEnterpriseIntro() {
		return enterpriseIntro;
	}
	/**
	 * 设置：企业备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：企业备注
	 */
	public String getRemark() {
		return remark;
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
