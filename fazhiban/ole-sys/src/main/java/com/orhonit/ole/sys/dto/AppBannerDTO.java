package com.orhonit.ole.sys.dto;

import java.util.Date;

import lombok.Data;

/**
 * app Banner图
 * 
 * @author zhangjy
 *
 */
@Data
public class AppBannerDTO {

	private Integer id; // 主键

	private String url;// 路径
	
	private String file_name;// 文件名
	
	private String content;// 描述
	
	private Integer sort;// 显示顺序
	
	private String if_show;// 是否展示
	
	private Integer role_id;// 角色ID
	
	private String role_name;// 角色名称
	
	private Date create_date;// 创建时间
	
	private Date update_date;// 更新时间
	
	private Integer del_flag;// 删除标记
	
}
