package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 组工画像
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-10 10:34:45
 */
@Data
@TableName("zg_portrayal")
public class ZgPortrayalEntity implements Serializable {
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
	 * 学一学数据
	 */
	private Integer studyRank;
	/**
	 * 思考力
	 */
	private Integer thinkRank;
	/**
	 * 执行力
	 */
	private Integer executeRank;
	/**
	 * 创新力
	 */
	private Integer innovateRank;
	/**
	 * 协同力
	 */
	private Integer synergyRank;
	/**
	 * 服务力
	 */
	private Integer serveRank;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 1-个人 2-科室
	 */
	private Integer type;

}
