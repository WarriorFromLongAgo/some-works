package com.orhonit.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 科室表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-30 17:07:15
 */
@Data
@TableName("ta_department_member")
public class TaDepartmentMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 科室id
	 */
	@TableId
	private Integer lowerId;
	/**
	 * 所属支部id
	 */
	private Integer deptId;
	/**
	 * 人员名称
	 */
	private String memberName;
	/**
	 * 职务
	 */
	private Integer memberPost;
	/**
	 * 上级科室名称
	 */
	private String deptName;
	/**
	 * 科室名称
	 */
	private String lowerName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 排序
	 */
	private Integer theSorting;
	/**
	 * 人员列表
	 */
	@TableField(exist = false)
	private List<SysUserEntity> userList;

}
