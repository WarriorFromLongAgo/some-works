package com.orhonit.modules.religion.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 民族字典
 * @author 	cyf
 * @date	2018/11/13 上午11:51:46
 */
@Data
@TableName("nation_code")
public class NationCode {

	@TableId
	private String id;
	private String nationCode;
	private String nationName;
	
}
