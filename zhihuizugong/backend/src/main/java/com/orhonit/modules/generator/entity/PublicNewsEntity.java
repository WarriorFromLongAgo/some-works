package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 智慧e家人才家园
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-14 14:41:18
 */
@Data
@TableName("public_news")
public class PublicNewsEntity implements Serializable {
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
	 * 内容
	 */
	private String content;
	/**
	 * 封面图片
	 */
	private String cover;
	/**
	 * 1-人才队伍 2-人才需求 3-项目申报 4-风采展示
	 */
	private Integer type;
	/**
	 * 创建人id
	 */
	private Long createUserId;
	/**
	 * 附件地址
	 */
	private String filePath;

}
