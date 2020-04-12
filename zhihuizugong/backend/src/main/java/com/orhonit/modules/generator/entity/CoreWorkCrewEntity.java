package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作队全队人员
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-10 10:11:20
 */
@Data
@TableName("core_work_crew")
public class CoreWorkCrewEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer id;
	/**
	 * 主表工作队id
	 */
	private String serveId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 所在科室
	 */
	private Integer lowerId;
	/**
	 * 科室名称
	 */
	private String lowerName;

}
