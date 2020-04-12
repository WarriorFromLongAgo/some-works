package com.orhonit.ole.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.report.entity.CaseEntity;

public interface CaseRepository extends JpaRepository<CaseEntity, String> , JpaSpecificationExecutor<CaseEntity>{
	
	

}
