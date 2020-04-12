package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.enforce.entity.CheckRecordEntity;

public interface CheckRecordRepository extends JpaRepository<CheckRecordEntity, String> , JpaSpecificationExecutor<CheckRecordEntity>{

	CheckRecordEntity findOneByCheckId(String checkId);
}
