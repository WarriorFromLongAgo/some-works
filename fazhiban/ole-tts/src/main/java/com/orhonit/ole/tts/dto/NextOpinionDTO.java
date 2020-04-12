package com.orhonit.ole.tts.dto;

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
