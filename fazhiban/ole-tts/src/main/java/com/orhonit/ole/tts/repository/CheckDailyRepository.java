package com.orhonit.ole.tts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.tts.entity.CheckDailyEntity;

public interface CheckDailyRepository extends JpaRepository<CheckDailyEntity, String> , JpaSpecificationExecutor<CheckDailyEntity>{
      
	CheckDailyEntity findByCheckNum(String checkNum);
}
