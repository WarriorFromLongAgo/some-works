package com.orhonit.modules.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 09:56:34
 */
@Data
@TableName("tb_document_content")
public class DocumentContentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 公文ID
	 */
	private String documentId;
	/**
	 * 创建时间
	 */
	private Date createdate;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 意见
	 */
	private String content;
	/**
	 * 人员ID
	 */
	private String userid;
	/**
	 * 人员名称
	 */
	private String userName;
	/**
	 * 部门ID
	 */
	private String deptid;
	/**
	 * 部门名称
	 */
	private String deptName;

}
