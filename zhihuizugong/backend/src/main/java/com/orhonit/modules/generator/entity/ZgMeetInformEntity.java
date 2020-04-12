package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 会议通知
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 15:05:24
 */
@Data
@TableName("zg_meet_inform")
public class ZgMeetInformEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 创建人id
	 */
	private Long userId;
	/**
	 * 会议主题
	 */
	private String meetTitle;
	/**
	 * 主持人
	 */
	private String meetHost;
	/**
	 * 汇报人
	 */
	private String meetReport;
	/**
	 * 会议地点
	 */
	private String meetSite;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;

}
