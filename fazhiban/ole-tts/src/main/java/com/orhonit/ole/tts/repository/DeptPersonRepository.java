package com.orhonit.ole.tts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.tts.entity.DeptPersonEntity;

public interface DeptPersonRepository extends JpaRepository<DeptPersonEntity, String>{
	
	DeptPersonEntity findById(String id);

}
