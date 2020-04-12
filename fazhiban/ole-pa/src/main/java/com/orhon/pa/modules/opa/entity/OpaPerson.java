package com.orhon.pa.modules.opa.entity;

import java.util.Date;

import com.orhon.pa.common.persistence.DataEntity;

/**
 * 人员Entity
 * @author 武跃忠
 *
 */
public class OpaPerson extends DataEntity<OpaPerson> {
    
	private static final long serialVersionUID = 1L;

	private String id;

    private String code;

    private String name;

    private String sex;

    private String nation;

    private String tel;

    private String political;

    private Date birthday;

    private String edu;

    private String cardNum;

    private String picture;

    private String duty;

    private String deptId;

    private String certNum;

    private String lawarea;

    private String certType;

    private String certAuth;

    private Date certTime;

    private Date certTerm;

    private String lawType;

    private String ifEffect;

    private String delFlag;

    private String createName;

    private Date createDate;

    private String updateName;

    private Date updateDate;

    private String mglName;

    private String mglCreateName;

    private String mglUpdateName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getLawarea() {
		return lawarea;
	}

	public void setLawarea(String lawarea) {
		this.lawarea = lawarea;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertAuth() {
		return certAuth;
	}

	public void setCertAuth(String certAuth) {
		this.certAuth = certAuth;
	}

	public Date getCertTime() {
		return certTime;
	}

	public void setCertTime(Date certTime) {
		this.certTime = certTime;
	}

	public Date getCertTerm() {
		return certTerm;
	}

	public void setCertTerm(Date certTerm) {
		this.certTerm = certTerm;
	}

	public String getLawType() {
		return lawType;
	}

	public void setLawType(String lawType) {
		this.lawType = lawType;
	}

	public String getIfEffect() {
		return ifEffect;
	}

	public void setIfEffect(String ifEffect) {
		this.ifEffect = ifEffect;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getMglName() {
		return mglName;
	}

	public void setMglName(String mglName) {
		this.mglName = mglName;
	}

	public String getMglCreateName() {
		return mglCreateName;
	}

	public void setMglCreateName(String mglCreateName) {
		this.mglCreateName = mglCreateName;
	}

	public String getMglUpdateName() {
		return mglUpdateName;
	}

	public void setMglUpdateName(String mglUpdateName) {
		this.mglUpdateName = mglUpdateName;
	}
    
    

}