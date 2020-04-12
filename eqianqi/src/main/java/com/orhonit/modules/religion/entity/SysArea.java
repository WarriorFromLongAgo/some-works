package com.orhonit.modules.religion.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName("sys_area")
@Data
public class SysArea {
	
	@TableId
	private String id; //
	private String name;//地区名称
	private String parentId;//父级id
	private String level;//等级

}
