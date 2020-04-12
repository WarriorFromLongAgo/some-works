package com.orhonit.ole.tts.service.checkQuery;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.tts.entity.LssuedEntity;

public interface CheckQueryService {


	List<LssuedEntity> getCaseList(Map<String, Object> params, Integer start, Integer length);

	Integer getCasecount(Map<String, Object> params);
	
	String getCheckZfryIdByCheckNum(String checkNum);
	
	Map<String,Object> getCheck(String checkNum);
}
