package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_ef_sms_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsEntity {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private Integer id;		//id
	
	private String telNum;	//手机号码
	
	private String content;	//短信内容
	
	private String status;	//短信状态
	
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss")
	private Date sendTime;		//发送时间
	
	@DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss")
	private Date createDate;		//创建日期
	
	private String createName;		//创建人名称
	
	private String createBy;		//创建人
	
	private String certNum;
	
	private String code;
}
