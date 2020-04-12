package com.orhonit.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
  * 组工书苑
 * @author YaoSC
 *
 */
@ApiModel
@Data
@TableName("learnalesson_bookstore")
public class LearnalessonBookstoreEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@TableId
    @ApiModelProperty(value = "书苑ID")
	private Integer bookstoreId;//编号

	private String bookstoreName;//书名

    private String bookstoreType;// 分类

    private String bookstoreAddress;//存储路径
    
    private String bookstorePic;    //封面
    
    private String describe; //描述
    
    private String catalog; //目录
    
    private Date createTime;//创建时间
    
    private Double leadabookIntegral;//积分
    
    private String bookAuther;//创建人
    
    private Integer readabookNumber; //人数

}
