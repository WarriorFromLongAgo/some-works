package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 民心连心桥表
 * 
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
@Data
@TableName("core_work")
public class CoreWorkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String serveId;
	/**
	 * 工作队id(1：包联工作队 2：志愿服务队 3：爱心乐超市)
	 */
	private Integer workId;
	/**
	 * 工作队简介视频
	 */
	private String workVideo;
	/**
	 * 工作队名称
	 */
	private String workName;
	/**
	 * 创建人ID
	 */
	private Long userId;
	/**
	 * 创建人
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private String creatTime;
	/**
	 * 服务地点
	 */
	private String serveSite;

}
