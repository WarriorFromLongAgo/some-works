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
@Table(name="ole_ef_review_record_item")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRecordItemEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer recordId;

	private Integer itemId;
	
	private Integer isTop;
	
	private String createName;
	
	private String 	createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
	
	@Transient
	private String itemName;
	
	@Transient
	private String itemContent;
	
	@Transient
	private String parentItemId;
	
	@Transient
	private String parentItemName;
	
	@Transient
	private String parentItemContent;
	
	@Transient
	private Integer score;
	
	@Transient
	private String remark;
	
	@Transient
	private String status;
}
