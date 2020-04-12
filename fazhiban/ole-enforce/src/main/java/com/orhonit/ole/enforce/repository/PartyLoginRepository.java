package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.PartyLoginEntity;

public interface PartyLoginRepository extends JpaRepository<PartyLoginEntity, String>{
	
}
