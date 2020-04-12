package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.SmsEntity;

public interface SmsRepository extends JpaRepository<SmsEntity, String>{
	
}
