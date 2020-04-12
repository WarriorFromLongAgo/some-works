package com.orhonit.ole.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.report.entity.PartyInfoEntity;

public interface PartyInfoRepository extends JpaRepository<PartyInfoEntity, String>{
	
	PartyInfoEntity findByCaseId(String caseId);

}
