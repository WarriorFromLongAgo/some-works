package com.orhonit.ole.enforce.dto;

import lombok.Data;

/**
 * 下一审批人
 * @author ebusu
 *
 */
@Data
public class NextOpinionDTO {

	private Long userId;
	
	private String userName;
	
	private String nickName;
}
