package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;



/**
 * Title: ApphelpMeEntity.java
 * @Description: 请你帮帮我 发帖信息
 * @author YaoSC
 * @date 2019年6月13日 
 */
@Data
@TableName("please_help_me")
public class ApphelpMeEntity implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer helpMeId;
	private String helpTitle;   //标题
	private String helpContent; //内容
	private Date helpCreateTime; //发布时间
	private Date helpUpdateTime; //更新时间
	private Integer helpUserId; //发布人
	@JSONField(jsonDirect=true)
	private String 	helpPicture;    //附件
	private Integer lowerId ;     //科室ID
	private  String lowerName;   //科室名称
	
	
}
