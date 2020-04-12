package com.orhonit.ole.enforce.dto.shareapi;

import lombok.Data;

/**
 * 获取文书模板字段
 * @author liubo
 *
 */
@Data
public class ApiDocFlowDTO {
	
	//获取id
	private String id;
	//获取名字
	private String name;
	//获取编码
	private String code;
	//获取是否必填
	private String needAdd;
	//文书模版内容
	private String content;
}
