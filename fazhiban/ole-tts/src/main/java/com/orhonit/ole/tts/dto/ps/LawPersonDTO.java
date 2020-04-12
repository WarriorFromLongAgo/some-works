package com.orhonit.ole.tts.dto.ps;

import lombok.Data;
@Data
public class LawPersonDTO {
/*	a.id areaId,
	a.`name` areaName,
	count(p.`name`) personCount,
	p.law_type lawType */
	private String areaId;
	private String areaName;
	private String personCount;
	private String lawType;
}
