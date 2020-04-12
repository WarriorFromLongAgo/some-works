package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * 
 * Title: JointCompanyEntity.java
 * @Description: 联席单位
 * @author YaoSC
 * @date 2019年7月19日
 */
@Data
@TableName("se_joint_company")
public class JointCompanyEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer id;  //主键
	private Integer sharingUnit;      //沟通共享单位
	private Integer fillinUserId;          //信息填报人
	private String mobilePhone;     //联系方式
	private String  email;           //邮箱
	private Double bonusPoints;      //积分奖励
	private String company;          //被反应人单位
	private String telephone;       //被反应人电话
	private Integer level;            //被反应人级别
	private String post;             //被反应人职务
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date levelTime;          //职级的时间
	private Date Illegality;         //首次违法时间
	private Date handle;             //处理违法时间
	private Integer disciplinarySanctions;  //党纪,政纪处分
	private Integer ImpactPeriod;    //影响期
	private Integer processing;      //组织处理情况
	private String situation;        //行政处罚,治安处罚
	private String content;          //主要内容
	private Integer questionCategories;    //问题类别
	private Integer subcategoriesOfIssues;  //问题系类
	private String title ;           //标题
	private String documentNumber;   //文号
	private String url ;             //附件路径
	private String wfwg;    //违反的法律法规条款
	private Date createTime;   //创建时间 
	private String reminder;    //未报提醒
	private Date updateTime;    //更新时间
	private String isDel;  //逻辑删  0:未删  1:已删
	private Integer userId; //被反应人ID
	//@TableField(exist = false)
	//private transient String orgName ; //单位名称
	
	
    
}
