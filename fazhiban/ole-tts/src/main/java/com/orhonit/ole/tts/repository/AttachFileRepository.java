package com.orhonit.ole.tts.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.tts.entity.AttachFileEntity;

public interface AttachFileRepository extends JpaRepository<AttachFileEntity, String>, JpaSpecificationExecutor<AttachFileEntity>{
	List<AttachFileEntity> findAllByCaseId(String caseId,Sort sort);
	
	List<AttachFileEntity> findAllByCaseId(String caseId);
	
	List<AttachFileEntity> findAllByCaseNum(String caseNum);
}
