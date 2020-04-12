package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.DeptPersonEntity;

public interface DeptPersonRepository extends JpaRepository<DeptPersonEntity, String>{
	
	DeptPersonEntity findById(String id);

}
