package com.orhonit.ole.sys.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ole_base_article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private String id;  //主键
	
	private String title;	//文章标题
	
	private String content;	//文章内容
	
	private String author;	//作者
	
	private String createName;	//创建人名字
	
	private String createBy;	//创建人账号
	
	private Date createDate;	//创建时间
	
	private String updateName;	//更新人名字
	
	private String updateBy;	//更新人账号
	
	private Date updateDate;	//更新时间
	
	private Integer isPs;	//是否公示
	
	private Integer delFlag;	//删除标志
	
	private Integer type;	//文章类型
	
	private String area;	//区划
}
