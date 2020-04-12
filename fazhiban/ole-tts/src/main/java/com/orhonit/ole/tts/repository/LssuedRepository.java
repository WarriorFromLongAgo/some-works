package com.orhonit.ole.tts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.tts.entity.LssuedEntity;

public interface LssuedRepository extends JpaRepository<LssuedEntity, String> , JpaSpecificationExecutor<LssuedEntity>{
	LssuedEntity findByCheckNum(String checkNum);
}
