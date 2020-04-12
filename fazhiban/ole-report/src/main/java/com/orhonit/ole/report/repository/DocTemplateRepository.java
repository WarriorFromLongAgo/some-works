package com.orhonit.ole.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.report.entity.DocTemplateEntity;

public interface DocTemplateRepository extends JpaRepository<DocTemplateEntity, String>, JpaSpecificationExecutor<DocTemplateEntity>{
	
}
