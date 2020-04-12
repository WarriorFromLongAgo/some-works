package com.orhonit.modules.religion.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * 活动场所
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Data
@TableName("religion_site")
public class ReligionSite implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @TableId
    private Long id;
	
	    //省
    private String shen;
	
	    //市
    private String shi;
	
	    //县
    private String xian;
	
	    //类型
    private String type;
	
	    //场所名称
    private String name;
	
	    //地址
    private String address;
	
	    //所属嘎查
    private String gacha;
	
	    //0寺观教堂   1固定处所
    private String siteType;
	
	    //批准设立机关
    private String ratifyEstablishOffice;
	
	    //批准设立机关时间
    private Timestamp ratifyEstablishTime;
	
	    //登记机关
    private String registerOffice;
	
	    //登记时间
    private Timestamp registerTime;
	
	    //登记id
    private String registerId;
	
	    //场所联系电话
    private String phone;
	
	    //场所建筑面积
    private Double area;
	
	    //占地面积
    private Double coverAnArea;
	
	    //常住人数
    private Integer residentNum;
	
	    //文物级别   
    private String relicLvl;
	
	    //备案
    private String putOnRecords;
	
	    //寺庙简介
    private String about;
	
	    //附件ids   ,号分隔
    private String fileIds;
	
	    //创建用户
    private Long createUser;
	
	    //创建时间
    private Timestamp createTime;
	

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
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：场所名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：场所名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：所属嘎查
	 */
	public void setGacha(String gacha) {
		this.gacha = gacha;
	}
	/**
	 * 获取：所属嘎查
	 */
	public String getGacha() {
		return gacha;
	}
	/**
	 * 设置：0寺观教堂   1固定处所
	 */
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	/**
	 * 获取：0寺观教堂   1固定处所
	 */
	public String getSiteType() {
		return siteType;
	}
	/**
	 * 设置：批准设立机关
	 */
	public void setRatifyEstablishOffice(String ratifyEstablishOffice) {
		this.ratifyEstablishOffice = ratifyEstablishOffice;
	}
	/**
	 * 获取：批准设立机关
	 */
	public String getRatifyEstablishOffice() {
		return ratifyEstablishOffice;
	}
	/**
	 * 设置：批准设立机关时间
	 */
	public void setRatifyEstablishTime(Timestamp ratifyEstablishTime) {
		this.ratifyEstablishTime = ratifyEstablishTime;
	}
	/**
	 * 获取：批准设立机关时间
	 */
	public Timestamp getRatifyEstablishTime() {
		return ratifyEstablishTime;
	}
	/**
	 * 设置：登记机关
	 */
	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}
	/**
	 * 获取：登记机关
	 */
	public String getRegisterOffice() {
		return registerOffice;
	}
	/**
	 * 设置：登记时间
	 */
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * 获取：登记时间
	 */
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	/**
	 * 设置：登记id
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * 获取：登记id
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * 设置：场所联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：场所联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：场所建筑面积
	 */
	public void setArea(Double area) {
		this.area = area;
	}
	/**
	 * 获取：场所建筑面积
	 */
	public Double getArea() {
		return area;
	}
	/**
	 * 设置：占地面积
	 */
	public void setCoverAnArea(Double coverAnArea) {
		this.coverAnArea = coverAnArea;
	}
	/**
	 * 获取：占地面积
	 */
	public Double getCoverAnArea() {
		return coverAnArea;
	}
	/**
	 * 设置：常住人数
	 */
	public void setResidentNum(Integer residentNum) {
		this.residentNum = residentNum;
	}
	/**
	 * 获取：常住人数
	 */
	public Integer getResidentNum() {
		return residentNum;
	}
	/**
	 * 设置：文物级别   
	 */
	public void setRelicLvl(String relicLvl) {
		this.relicLvl = relicLvl;
	}
	/**
	 * 获取：文物级别   
	 */
	public String getRelicLvl() {
		return relicLvl;
	}
	/**
	 * 设置：备案
	 */
	public void setPutOnRecords(String putOnRecords) {
		this.putOnRecords = putOnRecords;
	}
	/**
	 * 获取：备案
	 */
	public String getPutOnRecords() {
		return putOnRecords;
	}
	/**
	 * 设置：寺庙简介
	 */
	public void setAbout(String about) {
		this.about = about;
	}
	/**
	 * 获取：寺庙简介
	 */
	public String getAbout() {
		return about;
	}
	/**
	 * 设置：附件ids   ,号分隔
	 */
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	/**
	 * 获取：附件ids   ,号分隔
	 */
	public String getFileIds() {
		return fileIds;
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
