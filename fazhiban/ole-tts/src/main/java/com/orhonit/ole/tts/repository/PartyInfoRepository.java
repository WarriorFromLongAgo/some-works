package com.orhonit.ole.tts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.tts.entity.PartyInfoEntity;

public interface PartyInfoRepository extends JpaRepository<PartyInfoEntity, String>{
	
	PartyInfoEntity findByCaseId(String caseId);
}
