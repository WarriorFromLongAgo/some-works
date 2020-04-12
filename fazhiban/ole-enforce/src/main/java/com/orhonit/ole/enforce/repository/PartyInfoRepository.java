package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.PartyInfoEntity;

public interface PartyInfoRepository extends JpaRepository<PartyInfoEntity, String>{
	
	PartyInfoEntity findByCaseId(String caseId);
}
