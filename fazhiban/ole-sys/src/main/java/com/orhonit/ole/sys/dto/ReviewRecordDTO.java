package com.orhonit.ole.sys.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReviewRecordDTO {
	
	private Integer id;
	
	private String caseId;

	private Integer batch;
	
	private String  status;
	
	private Integer score;
	
	private String remark;
	
	private String createName;
	
	private String 	createBy;
	
	private Date createDate;
	
	private String updateName;
	
	private String updateBy;
	
	private Date updateDate;
	
	private String itemIdsStr;
	
	private String oneComment;
	
	private String twoComment;
	
	private List<ReviewRecordItemDTO> itemIds;
}
