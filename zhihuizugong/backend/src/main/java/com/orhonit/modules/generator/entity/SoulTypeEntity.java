package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;


/**
  * 分类
 * @author YaoSC
 *
 */
@Data
@TableName("soul_type")
public class SoulTypeEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private Integer typeId;  //编号
	
	private String typeName; //名称
	
	private Date createTime; //创建时间
	
	private Integer createUserId;//创建人
	
	private Integer typeIdentifier; //标识符: 1:书苑类型   2:视频类型

}
