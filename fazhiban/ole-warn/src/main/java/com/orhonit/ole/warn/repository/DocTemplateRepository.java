package com.orhonit.ole.warn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.warn.entity.DocTemplateEntity;

public interface DocTemplateRepository extends JpaRepository<DocTemplateEntity, String>, JpaSpecificationExecutor<DocTemplateEntity>{
	
}
