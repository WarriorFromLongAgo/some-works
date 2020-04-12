package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 会议通知和记录总结中间表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 17:17:20
 */
@Data
@TableName("zg_meet_record")
public class ZgMeetRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 会议通知id
	 */
	private String meetId;
	/**
	 * 内容
	 */
	private String meetContent;
	/**
	 * 1-会议记录 2-总结反馈
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 会议主题
	 */
	private String meetTitle;

}
