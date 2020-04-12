package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 个人画像十二边型默认值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 11:25:16
 */
@Data
@TableName("zg_default_thirteen")
public class ZgDefaultThirteenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 科室id
	 */
	private Integer lowerId;
	/**
	 * 科室名称
	 */
	private String lowerName;
	/**
	 * 答题考试默认值
	 */
	private Integer answer;
	/**
	 * 工作点子默认值
	 */
	private Integer workIdea;
	/**
	 * 成果分享默认值
	 */
	private Integer share;
	/**
	 * 组织生活默认值
	 */
	private Integer orgLive;
	/**
	 * 包联帮扶默认值
	 */
	private Integer help;
	/**
	 * 志愿服务默认值
	 */
	private Integer volunteer;
	/**
	 * 爱心捐助默认值
	 */
	private Integer donate;
	/**
	 * 履职尽责默认值
	 */
	private Integer resumption;
	/**
	 * 亮点工作默认值
	 */
	private Integer lightspot;
	/**
	 * 特色工作默认值
	 */
	private Integer feature;
	/**
	 * 理论学习默认值
	 */
	private Integer study;
	/**
	 * 思悟笔记默认值
	 */
	private Integer thinkNote;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
