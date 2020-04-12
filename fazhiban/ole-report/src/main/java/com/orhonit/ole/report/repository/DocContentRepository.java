package com.orhonit.ole.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.report.entity.DocContentEntity;

public interface DocContentRepository extends JpaRepository<DocContentEntity, String>{

	DocContentEntity findByTemplateIdAndCaseIdAndPartyId(String templateId, String caseId, String partyId);

}
