package com.orhonit.modules.generator.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class EverBodyShareVo implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分享ID
	 */
	private Integer shareId;
	
	/**
	 * 分享标题
	 */
	private String shareTitle;
	
	/**
	 * 分享内容
	 */
	private String shareContent;
	
	/**
	 * 保存路径
	 */
	private String shareUrl;
	
	/**
	 * 分享时间
	 */
	private Date shareCreateTime;
	
	/**
	 * 分享人
	 */
	private Long shareUserId;
	
	/**
	 * 上传类型:   1:图片 2:视频 3:其他
	 */
	private Integer shareType;
	
	/**
	 * 名字
	 */
	private String userTrueName;

}
