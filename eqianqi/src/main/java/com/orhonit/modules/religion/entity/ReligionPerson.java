package com.orhonit.modules.religion.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * 宗教人员
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@TableName("religion_person")
public class ReligionPerson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @TableId
    private Long id;
	
	    //姓名
    private String name;
	
	    //曾用名
    private String oldName;
    
    private String imgPath;
	
	    //称谓
   
    private String appellation;
	
	    //宗教
    private String religion;
	
	    //所属寺庙
    private Long theirTemple;
	
	    //教职身份
    private String noTenure;
	
	    //出生年月
    private String birthday;
	
	    //性别
    private Integer sex;
	
	    //民族
    private String nation;
    
    //民族
    private String nationName;
	
	    //教育程度
    private String educationDegree;
	
	    //毕业院校
    private String graduate;
	
	    //身份证
    private String idCard;
	
	    //联系电话
    private String phone;
	
	    //户籍地
    private String censusRegister;
	
	    //团体任职情况
    private String takeOfficeGroupAbout;
	
	    //院校任职情况
    private String takeOfficeAcademyAbout;
	
	    //活动场所任职情况
    private String activityAbout;
	
	    //证书单位
    private String certificateUnit;
	
	    //证书编号
    private String certificateId;
	
	    //证书颁发时间
    private Timestamp certificateTime;
	
	    //备案
    private String putOnRecords;
	
	    //备案时间
    private Timestamp putOnRecordsTime;
	
	    //省
    private String shen;
	
	    //市
    private String shi;
	
	    //县
    private String xian;
	
	    //备注
    private String remark;
	
	    //创建用户
    private Long createUser;
	
	    //创建时间
    private Timestamp createTime;
	

	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：曾用名
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	/**
	 * 获取：曾用名
	 */
	public String getOldName() {
		return oldName;
	}
	/**
	 * 设置：称谓
	 */
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	/**
	 * 获取：称谓
	 */
	public String getAppellation() {
		return appellation;
	}
	/**
	 * 设置：宗教
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}
	/**
	 * 获取：宗教
	 */
	public String getReligion() {
		return religion;
	}
	/**
	 * 设置：所属寺庙
	 */
	public void setTheirTemple(Long theirTemple) {
		this.theirTemple = theirTemple;
	}
	/**
	 * 获取：所属寺庙
	 */
	public Long getTheirTemple() {
		return theirTemple;
	}
	/**
	 * 设置：教职身份
	 */
	public void setNoTenure(String noTenure) {
		this.noTenure = noTenure;
	}
	/**
	 * 获取：教职身份
	 */
	public String getNoTenure() {
		return noTenure;
	}
	/**
	 * 设置：出生年月
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：出生年月
	 */
	public String getBirthday() {
		return birthday;
	}
	
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	/**
	 * 设置：教育程度
	 */
	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}
	/**
	 * 获取：教育程度
	 */
	public String getEducationDegree() {
		return educationDegree;
	}
	/**
	 * 设置：毕业院校
	 */
	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}
	/**
	 * 获取：毕业院校
	 */
	public String getGraduate() {
		return graduate;
	}
	/**
	 * 设置：身份证
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * 获取：身份证
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：户籍地
	 */
	public void setCensusRegister(String censusRegister) {
		this.censusRegister = censusRegister;
	}
	/**
	 * 获取：户籍地
	 */
	public String getCensusRegister() {
		return censusRegister;
	}
	/**
	 * 设置：团体任职情况
	 */
	public void setTakeOfficeGroupAbout(String takeOfficeGroupAbout) {
		this.takeOfficeGroupAbout = takeOfficeGroupAbout;
	}
	/**
	 * 获取：团体任职情况
	 */
	public String getTakeOfficeGroupAbout() {
		return takeOfficeGroupAbout;
	}
	/**
	 * 设置：院校任职情况
	 */
	public void setTakeOfficeAcademyAbout(String takeOfficeAcademyAbout) {
		this.takeOfficeAcademyAbout = takeOfficeAcademyAbout;
	}
	/**
	 * 获取：院校任职情况
	 */
	public String getTakeOfficeAcademyAbout() {
		return takeOfficeAcademyAbout;
	}
	/**
	 * 设置：活动场所任职情况
	 */
	public void setActivityAbout(String activityAbout) {
		this.activityAbout = activityAbout;
	}
	/**
	 * 获取：活动场所任职情况
	 */
	public String getActivityAbout() {
		return activityAbout;
	}
	/**
	 * 设置：证书单位
	 */
	public void setCertificateUnit(String certificateUnit) {
		this.certificateUnit = certificateUnit;
	}
	/**
	 * 获取：证书单位
	 */
	public String getCertificateUnit() {
		return certificateUnit;
	}
	/**
	 * 设置：证书编号
	 */
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	/**
	 * 获取：证书编号
	 */
	public String getCertificateId() {
		return certificateId;
	}
	/**
	 * 设置：证书颁发时间
	 */
	public void setCertificateTime(Timestamp certificateTime) {
		this.certificateTime = certificateTime;
	}
	/**
	 * 获取：证书颁发时间
	 */
	public Timestamp getCertificateTime() {
		return certificateTime;
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
	 * 设置：备案时间
	 */
	public void setPutOnRecordsTime(Timestamp putOnRecordsTime) {
		this.putOnRecordsTime = putOnRecordsTime;
	}
	/**
	 * 获取：备案时间
	 */
	public Timestamp getPutOnRecordsTime() {
		return putOnRecordsTime;
	}
	/**
	 * 设置：省
	 */
	
	
	
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getShen() {
		return shen;
	}
	public void setShen(String shen) {
		this.shen = shen;
	}
	public String getShi() {
		return shi;
	}
	public void setShi(String shi) {
		this.shi = shi;
	}
	public String getXian() {
		return xian;
	}
	public void setXian(String xian) {
		this.xian = xian;
	}
	/**
	 * 获取：备注
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
