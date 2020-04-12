package com.orhonit.ole.enforce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.DocContentEntity;

public interface DocContentRepository extends JpaRepository<DocContentEntity, String>{

	DocContentEntity findByTemplateIdAndCaseIdAndPartyId(String templateId, String caseId, String partyId);
	
	List<DocContentEntity> findByTemplateIdAndCaseId(String templateId, String caseId);

}
