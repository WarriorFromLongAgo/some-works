package com.orhonit.ole.server.service;

import com.orhonit.ole.server.model.Lesection;
import com.orhonit.ole.server.model.LtcAtt;


/**
 * 日志service
 * 
 * @author caodw
 *
 *
 */
public interface LesAttService {

    int saveSection(Lesection lesection);
	
	Lesection updateSection(Lesection lesection);
	
	void delSection(long id);

	Lesection getSection(String username);
	
	Lesection getSectionById(Long userId);
}
