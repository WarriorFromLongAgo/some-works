package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * 
 * Title: SupervisoryEntity.java
 * @Description:干部职工个人监督信息
 * @author YaoSC
 * @date 2019年7月20日
 */
//@Data
@TableName("se_supervisory_information")
public class SupervisoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer id;     //主键
	private Integer userId;    //用户ID
	private String idNumber;  //身份证号
	private String contactNumber;   //联系电话
	private String post ;      //职务
	private Integer compilation;   //编制情况
	private Integer level;    //级别
	private String partTimeSituation;    //兼任职情况
	private Integer late;      //迟到次数
	private Integer earlyRetirement;      //早退
	private Integer miner;      //矿工
	private Integer sickLeave;     //病假
	private Integer compassionateLeave;    //事假
	private Integer vacation;     //休假
	private String sickUrl;     //病假假条
	private String compassionateUrl;   //事假假条
	private String vacationUrl;   //休假假条
	private Integer goOut;   //外出
	private String goOutUrl;  //外出条子
	private Integer train;    //培训天数
	private Integer onBusiness;   //出差地
	private Integer exit;    //出国境证件类型
	private Integer safekeeping;    //保管机构
	private Integer spouseExit;      //	配偶,子女移居国证件
	private Integer spouseSafekeeping;   //配偶,子女证件保管机构
	@JSONField(jsonDirect=true)
	private String mainWork;   //工作  work:主要工作     complete：完成情况
	@JSONField(jsonDirect=true)
	private String project;    //pj:项目    complete：完成情况
	private Integer punishment;     //党纪、政纪处分
	private Integer Influence;    //影响
	private Integer handle;   //组织处理情况
	private String punish;    //行政处罚、治安处罚、刑事处罚情况
	private String law;      //违反的法律法规条款
	private String content;   //主要内容
	private Integer problem;   //问题类别
	private Integer problemXl;  //问题细类
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date IllegalityTime; //首次违法时间
	private Integer unitId;   //报送单位ID'
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;    //录入时间
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date handleTime; //处理时间
	private String trainUrl;  //培训请假条
	private Integer cengMian;  //层面
	private String isDel;   //逻辑删  0-未删  1-已删
	private Date updateTime;  //更新时间
	@TableField(exist = false)
    private String userName ; //用户名称
	@Override
	public String toString() {
		return "SupervisoryEntity [id=" + id + ", userId=" + userId + ", idNumber=" + idNumber + ", contactNumber="
				+ contactNumber + ", post=" + post + ", compilation=" + compilation + ", level=" + level
				+ ", partTimeSituation=" + partTimeSituation + ", late=" + late + ", earlyRetirement=" + earlyRetirement
				+ ", miner=" + miner + ", sickLeave=" + sickLeave + ", compassionateLeave=" + compassionateLeave
				+ ", vacation=" + vacation + ", sickUrl=" + sickUrl + ", compassionateUrl=" + compassionateUrl
				+ ", vacationUrl=" + vacationUrl + ", goOut=" + goOut + ", goOutUrl=" + goOutUrl + ", train=" + train
				+ ", onBusiness=" + onBusiness + ", exit=" + exit + ", safekeeping=" + safekeeping + ", spouseExit="
				+ spouseExit + ", spouseSafekeeping=" + spouseSafekeeping + ", mainWork=" + mainWork + ", project="
				+ project + ", punishment=" + punishment + ", Influence=" + Influence + ", handle=" + handle
				+ ", punish=" + punish + ", law=" + law + ", content=" + content + ", problem=" + problem
				+ ", problemXl=" + problemXl + ", IllegalityTime=" + IllegalityTime + ", unitId=" + unitId
				+ ", createTime=" + createTime + ", handleTime=" + handleTime + ", trainUrl=" + trainUrl + ", cengMian="
				+ cengMian + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Integer getCompilation() {
		return compilation;
	}
	public void setCompilation(Integer compilation) {
		this.compilation = compilation;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPartTimeSituation() {
		return partTimeSituation;
	}
	public void setPartTimeSituation(String partTimeSituation) {
		this.partTimeSituation = partTimeSituation;
	}
	public Integer getLate() {
		return late;
	}
	public void setLate(Integer late) {
		this.late = late;
	}
	public Integer getEarlyRetirement() {
		return earlyRetirement;
	}
	public void setEarlyRetirement(Integer earlyRetirement) {
		this.earlyRetirement = earlyRetirement;
	}
	public Integer getMiner() {
		return miner;
	}
	public void setMiner(Integer miner) {
		this.miner = miner;
	}
	public Integer getSickLeave() {
		return sickLeave;
	}
	public void setSickLeave(Integer sickLeave) {
		this.sickLeave = sickLeave;
	}
	public Integer getCompassionateLeave() {
		return compassionateLeave;
	}
	public void setCompassionateLeave(Integer compassionateLeave) {
		this.compassionateLeave = compassionateLeave;
	}
	public Integer getVacation() {
		return vacation;
	}
	public void setVacation(Integer vacation) {
		this.vacation = vacation;
	}
	public String getSickUrl() {
		return sickUrl;
	}
	public void setSickUrl(String sickUrl) {
		this.sickUrl = sickUrl;
	}
	public String getCompassionateUrl() {
		return compassionateUrl;
	}
	public void setCompassionateUrl(String compassionateUrl) {
		this.compassionateUrl = compassionateUrl;
	}
	public String getVacationUrl() {
		return vacationUrl;
	}
	public void setVacationUrl(String vacationUrl) {
		this.vacationUrl = vacationUrl;
	}
	public Integer getGoOut() {
		return goOut;
	}
	public void setGoOut(Integer goOut) {
		this.goOut = goOut;
	}
	public String getGoOutUrl() {
		return goOutUrl;
	}
	public void setGoOutUrl(String goOutUrl) {
		this.goOutUrl = goOutUrl;
	}
	public Integer getTrain() {
		return train;
	}
	public void setTrain(Integer train) {
		this.train = train;
	}
	public Integer getOnBusiness() {
		return onBusiness;
	}
	public void setOnBusiness(Integer onBusiness) {
		this.onBusiness = onBusiness;
	}
	public Integer getExit() {
		return exit;
	}
	public void setExit(Integer exit) {
		this.exit = exit;
	}
	public Integer getSafekeeping() {
		return safekeeping;
	}
	public void setSafekeeping(Integer safekeeping) {
		this.safekeeping = safekeeping;
	}
	public Integer getSpouseExit() {
		return spouseExit;
	}
	public void setSpouseExit(Integer spouseExit) {
		this.spouseExit = spouseExit;
	}
	public Integer getSpouseSafekeeping() {
		return spouseSafekeeping;
	}
	public void setSpouseSafekeeping(Integer spouseSafekeeping) {
		this.spouseSafekeeping = spouseSafekeeping;
	}
	public String getMainWork() {
		return mainWork;
	}
	public void setMainWork(String mainWork) {
		this.mainWork = mainWork;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getPunishment() {
		return punishment;
	}
	public void setPunishment(Integer punishment) {
		this.punishment = punishment;
	}
	public Integer getInfluence() {
		return Influence;
	}
	public void setInfluence(Integer influence) {
		Influence = influence;
	}
	public Integer getHandle() {
		return handle;
	}
	public void setHandle(Integer handle) {
		this.handle = handle;
	}
	public String getPunish() {
		return punish;
	}
	public void setPunish(String punish) {
		this.punish = punish;
	}
	public String getLaw() {
		return law;
	}
	public void setLaw(String law) {
		this.law = law;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getProblem() {
		return problem;
	}
	public void setProblem(Integer problem) {
		this.problem = problem;
	}
	public Integer getProblemXl() {
		return problemXl;
	}
	public void setProblemXl(Integer problemXl) {
		this.problemXl = problemXl;
	}
	//@JsonSerialize(using = Date2LongSerializer.class)
	public Date getIllegalityTime() {
		return IllegalityTime;
	}
	public void setIllegalityTime(Date illegalityTime) {
		IllegalityTime = illegalityTime;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public String getTrainUrl() {
		return trainUrl;
	}
	public void setTrainUrl(String trainUrl) {
		this.trainUrl = trainUrl;
	}
	public Integer getCengMian() {
		return cengMian;
	}
	public void setCengMian(Integer cengMian) {
		this.cengMian = cengMian;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserName() {
		return userName;
	}

}
