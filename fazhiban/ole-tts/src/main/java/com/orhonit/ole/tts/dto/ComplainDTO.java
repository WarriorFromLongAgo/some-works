package com.orhonit.ole.tts.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 投诉信息DTO
 * @author 武跃忠
 *
 */
@Data
public class ComplainDTO {
	
	private int id;
	
	private String name;
	
	private String email;
	
	private String lang;
	
	private String content;
	
	private String tel;
	
	private String address;
	
	private String createName;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	private String createBy;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	private String updateName;
	
	private String updateBy;
}
