package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
 * 
 * Title: SubmittingEntity.java
 * @Description:自查自纠  报送单位
 * @author YaoSC
 * @date 2019年7月20日
 */
@Data
@TableName("se_submitting_unit")
public class SubmittingEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer id;       //主键
	private Integer orgId;    //报送单位ID
	private Integer auditorId;       //单位审核人ID
	private Integer tianbaoUserId;   //填报人ID
	private String telephone;     //联系电话
	private Integer ldNumber;     //领导职数
	private Integer administrationNumber;   //行政编制数
	private Integer causeNumber;     //事业编制数
	private Integer workmanshipNumber;   //工勤编制数
	private Integer hireNumber;      //雇佣编制数
	private Integer traineeNumber;     //见习生人数
	private Integer temporaryWorkerNumber;  //临时工
	private Integer retireesNumber;      //退休人数
	private Integer scoreRanking;     //积分排名
	private String  workTask;     //工作任务
	private Integer userId ;    //当前登录人ID
	private Date createTime;     //创建时间
	private Integer cadresNumber;   //干部职工人数
	private Date updateTime ; //更新时间
	private String isDel;     //逻辑删  0-未删  1-已删
	

    
	
}
