package com.orhonit.modules.generator.app.vo;

import java.util.Date;

import lombok.Data;

/**
 * 违纪违法
 * Title: AppWjwfVO.java
 * @Description:
 * @author YaoSC
 * @date 2019年8月19日
 */
@Data
public class AppWjwfVO {
	private Integer punishment;   //党纪、政纪处分
	private Integer Influence;    //影响期
	private Integer handle;      //组织处理情况
	private String punish;       //行政处罚、治安处罚、刑事处罚情况
	private String content;     //zhuyao neirong 
	private Date updateTime;     //更新时间
	private Integer cengMian;    //层面

}
