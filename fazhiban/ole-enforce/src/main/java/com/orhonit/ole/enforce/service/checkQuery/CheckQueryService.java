package com.orhonit.ole.enforce.service.checkQuery;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.enforce.entity.LssuedEntity;

public interface CheckQueryService {


	List<LssuedEntity> getCaseList(Map<String, Object> params, Integer start, Integer length);

	List<LssuedEntity> getCaseListCopy(Map<String, Object> params, Integer start, Integer length);
	
	Integer getCasecount(Map<String, Object> params);
	
	Integer getCount(Map<String, Object> params);
	
	String getCheckZfryIdByCheckNum(String checkNum);
}
