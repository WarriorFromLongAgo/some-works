package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 资讯表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-15 17:40:51
 */
@Data
@TableName("zg_information")
public class ZgInformationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 总编
	 */
	private String chiefEditor;
	/**
	 * 执行总编
	 */
	private String managingEditor;
	/**
	 * 责任编辑
	 */
	private String dutyEditor;
	/**
	 * 封面图片
	 */
	private String cover;
	/**
	 * 是否置顶 0-否 1-是
	 */
	private Integer isStick;

}
