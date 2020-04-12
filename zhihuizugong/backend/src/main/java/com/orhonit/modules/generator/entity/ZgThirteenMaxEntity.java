package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 个人十二边型画像最高值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-21 15:30:46
 */
@Data
@TableName("zg_thirteen_max")
public class ZgThirteenMaxEntity implements Serializable {
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
	 * 创建人姓名
	 */
	private String userName;
	/**
	 * 答题考试最高值
	 */
	private Integer answer;
	/**
	 * 工作点子最高值
	 */
	private Integer workIdea;
	/**
	 * 成果分享最高值
	 */
	private Integer share;
	/**
	 * 组织生活最高值
	 */
	private Integer orgLive;
	/**
	 * 包联帮扶最高值
	 */
	private Integer help;
	/**
	 * 志愿服务最高值
	 */
	private Integer volunteer;
	/**
	 * 爱心捐助最高值
	 */
	private Integer donate;
	/**
	 * 履职尽责最高值
	 */
	private Integer resumption;
	/**
	 * 亮点工作最高值
	 */
	private Integer lightspot;
	/**
	 * 特色工作最高值
	 */
	private Integer feature;
	/**
	 * 理论学习最高值
	 */
	private Integer study;
	/**
	 * 思悟笔记最高值
	 */
	private Integer thinkNote;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
