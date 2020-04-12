package com.orhonit.ole.server.service;

import com.orhonit.ole.server.model.Lesection;

public interface SectionService {

	int saveSection(Lesection lesection);
	
	Lesection updateSection(Lesection lesection);
	
	void delSection(long id);

	Lesection getSection(String username);
	
	Lesection getSectionById(Long userId);
	
}
