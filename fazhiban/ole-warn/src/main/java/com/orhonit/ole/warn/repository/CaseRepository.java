package com.orhonit.ole.warn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.warn.entity.CaseEntity;

public interface CaseRepository extends JpaRepository<CaseEntity, String> , JpaSpecificationExecutor<CaseEntity>{
	
	

}
