package com.orhonit.ole.warn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.warn.entity.PartyInfoEntity;

public interface PartyInfoRepository extends JpaRepository<PartyInfoEntity, String>{
	
	PartyInfoEntity findByCaseId(String caseId);

}
