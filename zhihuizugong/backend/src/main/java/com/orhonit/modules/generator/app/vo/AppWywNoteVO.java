package com.orhonit.modules.generator.app.vo;


import lombok.Data;


/**
 * 捂一捂    排行
 * @author YaoSC
 *
 */
@Data
public class AppWywNoteVO {
	
	
	private Integer index;  //排名
	
	private Integer numberOfReleases;//发布数量
	
	private String userName;  //姓名
	
	private String proportion;//发布占比例

}
