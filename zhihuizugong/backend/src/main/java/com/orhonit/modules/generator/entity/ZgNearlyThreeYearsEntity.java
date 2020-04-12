package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 近三年考核情况,奖惩
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-19 16:50:11
 */
@Data
@TableName("zg_nearly_three_years")
public class ZgNearlyThreeYearsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 年份
	 */
	private String particularYear;
	/**
	 * 身份证号
	 */
	private String userNickname;
	/**
	 * 奖励情况
	 */
	private String award;
	/**
	 * 受处分情况
	 */
	private String punishment;
	/**
	 * 考核情况
	 */
	private String assess;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;

}
