package com.orhonit.modules.generator.entity;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户主要家庭成员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-23 11:11:38
 */
@Data
@TableName("zg_family_member")
public class ZgFamilyMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 称谓
	 */
	private String appellation;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 出生年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date dateOfBirth;
	/**
	 * 政治面貌
	 */
	private String politics;
	/**
	 * 工作单位及职务
	 */
	private String position;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;

}
