package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户扩展表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 14:18:29
 */
@Data
@TableName("sys_user_extend")
public class SysUserExtendEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 出生地
	 */
	private String birthplace;
	/**
	 * 入党时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date partyTime;
	/**
	 * 健康状况
	 */
	private String healthStatus;
	/**
	 * 参加工作时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date workTime;
	/**
	 * 专业技术职称
	 */
	private String majorDuty;
	/**
	 * 全日制最高学历
	 */
	private String education;
	/**
	 * 全日制最高学位
	 */
	private String degree;
	/**
	 * 毕业院校及专业
	 */
	private String schoolMajor;
	/**
	 * 身份证号
	 */
	private String userNickname;
	/**
	 * 结对帮扶
	 */
	private String bothHelp;
	/**
	 * 日志
	 */
	private String log;
	/**
	 * 专长
	 */
	private String strongPoint;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	/**
	 * 工作岗位
	 */
	private String workPost;
	/**
	 * 组工讲堂
	 */
	//private String lecture;
	/**
	 * 组工书苑
	 */
	//private String books;
	/**
	 * 学习强国
	 */
	//private String study;
	/**
	 * 法宣在线
	 */
	//private String lay;
	/**
	 * 用户名
	 */
	private String userTrueName;
	/**
	 * 性别
	 */
	private Integer userSex;
	/**
	 * 民族
	 */
	private Integer userRace;
	/**
	 * 出生日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date userBirthday;
	/**
	 * 籍贯
	 */
	private String nativePlace;
	/**
	 * 转正日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date becomeTime;
	/**
	 * 人员类别
	 */
	private String userType;
	/**
	 * 现任职务
	 */
	private String position;
	/**
	 * 1-党员 2-干部
	 */
	private String type;
	/**
	 * 工作单位及职务
	 */
	private String workAndPosition;
	/**
	 * 任现职务时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date presentJobTime;
	/**
	 * 职务层次
	 */
	private String jobLevel;
	/**
	 * 现职级
	 */
	private String rank;
	/**
	 * 专业技术类公务员任职资格
	 */
	private String professionalQualification;
	/**
	 * 管理类别
	 */
	private String manageType;
	/**
	 * 编制类别
	 */
	private String authorizedType;
	/**
	 * 公务员登记时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date civilTime;
	/**
	 * 是否两年以上基层工作经验
	 */
	private String twoYearsJob;
	/**
	 * 人员管理状态
	 */
	private String personManageStatus;
	/**
	 * 最高学历
	 */
	private String highestEducation;
	/**
	 * 最高学历学校
	 */
	private String highestEducationSchool;
	/**
	 * 最高学位
	 */
	private String highestDegree;
	/**
	 * 最高学位学校
	 */
	private String highestDegreeSchool;
	/**
	 * 支部党员大会参加次数
	 */
	private String deptPartyMeet;
	/**
	 * 支部委员会参加次数
	 */
	private String deptMemberMeet;
	/**
	 * 党小组会参加次数
	 */
	private String partyGroupMeet;
	/**
	 * 党课参加次数
	 */
	private String partyClass;
}
