package com.orhonit.ole.sys.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ole_ef_review_item")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewItemEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name; //评查内容
	
	private String content; //评分方法及标准
	
	private Integer parentId; //父级分类ID
	
	private String parentName; //父级分类ID
	
	private Integer sort;
	
	private Integer  score;
	
	private String ifEffect;
	
	private String 	delFlag;
	
	private String 	createBy;
	
	private Date createDate;
	
	private String createName;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
	
	@Transient
	private Integer isSelected;
	
	@Transient
	private Integer scoreSum;
	
	@Transient
	private String remark;
	
	@Transient
	private String status;
	
}
