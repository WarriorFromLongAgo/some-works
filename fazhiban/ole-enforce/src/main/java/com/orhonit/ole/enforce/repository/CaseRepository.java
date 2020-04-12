package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.orhonit.ole.enforce.entity.CaseEntity;

public interface CaseRepository extends JpaRepository<CaseEntity, String> , JpaSpecificationExecutor<CaseEntity>{
	
	@Query(value="select getBaseDeptByParentId(?1)", nativeQuery=true)
	String getDeptUserByCurrentUser(String deptId);

	CaseEntity findByCaseNum(String caseNum);

}
