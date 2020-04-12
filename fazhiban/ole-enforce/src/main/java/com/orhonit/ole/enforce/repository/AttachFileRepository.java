package com.orhonit.ole.enforce.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.enforce.entity.AttachFileEntity;

public interface AttachFileRepository extends JpaRepository<AttachFileEntity, String>, JpaSpecificationExecutor<AttachFileEntity>{
	List<AttachFileEntity> findAllByCaseId(String caseId,Sort sort);
	
	List<AttachFileEntity> findAllByCaseNum(String caseNum);
}
