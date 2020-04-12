package com.orhonit.ole.sys.dto;

import java.util.Date;

import lombok.Data;

/**
 * app版本
 * 
 * @author 武耀忠
 *
 */
@Data
public class VersionDTO {

	private int id; // 主键

	private int version_code;// 版本号
	
	private int min_support;// 最低支持版本
	
	private String version_name;// 版本名称
	
	private String apk_name;// apk文件名
	
	private String content;// 版本更新日志
	
	private int flag;// 最新版本标志
	
	private Date create_date;// 创建时间
	
	private String create_name;// 创建人名称
	
	private String create_by;// 创建人账号
	
	private Date update_date;// 更新时间
	
	private String update_name;// 更新人名称
	
	private String update_by;// 更新人账号

}
