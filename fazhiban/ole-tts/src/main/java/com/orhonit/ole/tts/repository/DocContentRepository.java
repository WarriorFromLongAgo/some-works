package com.orhonit.ole.tts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.tts.entity.DocContentEntity;

public interface DocContentRepository extends JpaRepository<DocContentEntity, String>{

	DocContentEntity findByTemplateIdAndCaseIdAndPartyId(String templateId, String caseId, String partyId);
	
	DocContentEntity findByTemplateIdAndCaseId(String templateId, String caseId);

}
