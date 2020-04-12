package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_ef_app_log")
@AllArgsConstructor
@NoArgsConstructor
public class AppLogEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String logId;
	
	private String startTime;
	
	private String endTime;
	
	private String url;
	
	private String httpMethod;
	
	private String token;
	
	private String userId;
	
	private String result;
	
	private String execTime;
	
	private String userAgent;
	
	private Integer isSuccess;
	
	private String params;
	
	private Date createDate;
}
