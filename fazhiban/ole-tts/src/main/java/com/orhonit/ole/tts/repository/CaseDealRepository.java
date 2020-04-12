package com.orhonit.ole.tts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.tts.entity.CaseDealEntity;


public interface CaseDealRepository extends JpaRepository<CaseDealEntity, String>{

	List<CaseDealEntity> findByCaseId(String caseId);

}
