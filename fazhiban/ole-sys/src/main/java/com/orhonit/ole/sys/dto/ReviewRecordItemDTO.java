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

@Data
public class ReviewRecordItemDTO {
	
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
}
