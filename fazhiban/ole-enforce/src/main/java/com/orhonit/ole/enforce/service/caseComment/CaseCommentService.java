package com.orhonit.ole.enforce.service.caseComment;

import java.util.List;

import com.orhonit.ole.enforce.entity.CaseCommentEntity;

public interface CaseCommentService {
	
	void save(CaseCommentEntity caseCommentEntity);
	
	List<CaseCommentEntity> getListByCaseId(String caseId);
}
