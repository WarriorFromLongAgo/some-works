package com.orhonit.ole.report.dto;

import lombok.Data;

@Data
public class LawYearDTO {

	private String effectLevel; //法律类型
	private String effectLevelId; //法律类型Id
	private String publishDate;//年份
	private String lawCount; //法律条数
	private String name;//法律名称
}
