package com.orhonit.ole.enforce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.enforce.entity.CaseDealEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;

public interface CaseDealRepository extends JpaRepository<CaseDealEntity, String>, JpaSpecificationExecutor<CheckDailyEntity>{

	List<CaseDealEntity> findByCaseId(String caseId);

}
