package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 组工画像科室默认值
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-19 14:43:39
 */
@Data
@TableName("zg_default_score_dept")
public class ZgDefaultScoreDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 科室id
	 */
	private Integer lowerId;
	/**
	 * 科室名称
	 */
	private String lowerName;
	/**
	 * 学习力默认值
	 */
	private Integer studyDefault;
	/**
	 * 思考力默认值
	 */
	private Integer thinkDefault;
	/**
	 * 执行力默认值
	 */
	private Integer executeDefault;
	/**
	 * 创新力默认值
	 */
	private Integer innovateDefault;
	/**
	 * 协同力默认值
	 */
	private Integer synergyDefault;
	/**
	 * 服务力默认值
	 */
	private Integer serveDefault;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
