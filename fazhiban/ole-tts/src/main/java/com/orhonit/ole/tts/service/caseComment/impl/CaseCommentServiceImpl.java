package com.orhonit.ole.tts.service.caseComment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.tts.dao.CaseCommentDao;
import com.orhonit.ole.tts.entity.CaseCommentEntity;
import com.orhonit.ole.tts.service.caseComment.CaseCommentService;


@Service
public class CaseCommentServiceImpl implements CaseCommentService {
	
	@Autowired
	private CaseCommentDao caseCommentDao ;
	
	@Override
	public void save(CaseCommentEntity caseCommentEntity) {
		this.caseCommentDao.save(caseCommentEntity);
	}

	@Override
	public List<CaseCommentEntity> getListByCaseId(String caseId) {
		
		return this.caseCommentDao.caseCommentList(caseId);
	}

}
