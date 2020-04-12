package com.orhonit.ole.enforce.dto.api;

import lombok.Data;

@Data
public class ApiCaseDocDTO {
	private String caseNum;// 案件id/检查 id
	private String code;// 文书模板编号
	private String name;//文书名称
	private String value; //文书内容
}
