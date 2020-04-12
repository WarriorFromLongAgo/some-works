package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.enforce.entity.LssuedEntity;

public interface LssuedRepository extends JpaRepository<LssuedEntity, String> , JpaSpecificationExecutor<LssuedEntity>{
	LssuedEntity findByCheckNum(String checkNum);
}
