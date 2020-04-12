package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 栏目类别表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-12 14:32:36
 */
@Data
@TableName("tb_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 栏目ID 主键
	 */
	@TableId
	private Integer catId;
	/**
	 * 站点id
	 */
	private String siteid;
	/**
	 * 所属模块
	 */
	private String module;
	/**
	 * 类别
	 */
	private String type;
	/**
	 * 父级ID
	 */
	private String parentId;
	/**
	 *  模型id
	 */
	private String modelId;
	/**
	 *  所有父id
	 */
	private String arrParentId;
	/**
	 * 是否存在子栏目  1 存在
	 */
	private Integer child;
	/**
	 * 所有子栏目ID
	 */
	private String arrChildId;
	/**
	 * 栏目名称
	 */
	private String catName;
	/**
	 * 封面
	 */
	private String image;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 目录
	 */
	private String catDir;
	/**
	 * 父目录
	 */
	private String parentDir;
	/**
	 * 排序
	 */
	private Integer listorder;
	/**
	 * 是否显示  1 显示
	 */
	private Integer ismenu;
	/**
	 * 是否删除 1 是 2 否
	 */
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
