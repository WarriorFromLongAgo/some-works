package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.enforce.entity.CheckDailyEntity;

public interface CheckDailyRepository extends JpaRepository<CheckDailyEntity, String> , JpaSpecificationExecutor<CheckDailyEntity>{
      
	CheckDailyEntity findByCheckNum(String checkNum);
	
	CheckDailyEntity findByCheckId(String checkId);
}
