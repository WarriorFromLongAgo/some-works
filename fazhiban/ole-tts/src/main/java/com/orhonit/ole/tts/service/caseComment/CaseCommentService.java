package com.orhonit.ole.tts.service.caseComment;

import java.util.List;

import com.orhonit.ole.tts.entity.CaseCommentEntity;

public interface CaseCommentService {
	
	void save(CaseCommentEntity caseCommentEntity);
	
	List<CaseCommentEntity> getListByCaseId(String caseId);
}
