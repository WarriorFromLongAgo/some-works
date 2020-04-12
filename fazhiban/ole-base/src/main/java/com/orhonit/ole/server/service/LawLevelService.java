package com.orhonit.ole.server.service;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.server.model.LawLevel;

/**
 * 法律级别service
 * 
 * @author zhangjy
 *
 *
 */
public interface LawLevelService {
	
	int count(Map<String, Object> param);
	
	List<LawLevel> list(Map<String, Object> param , Integer start , Integer length);
	
	String getNameById(String parentId);
	
	String getMglNameById(String parentId);
	
	List<LawLevel> getAllLawLevel();
	
	void save(LawLevel lawLevel);
	
	void updateDel(String id);
	
	LawLevel getInfoById (String id);
	
	void update(LawLevel lawLevel);
}
