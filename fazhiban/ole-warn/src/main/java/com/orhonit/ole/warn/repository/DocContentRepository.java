package com.orhonit.ole.warn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.warn.entity.DocContentEntity;

public interface DocContentRepository extends JpaRepository<DocContentEntity, String>{

	DocContentEntity findByTemplateIdAndCaseIdAndPartyId(String templateId, String caseId, String partyId);

}
