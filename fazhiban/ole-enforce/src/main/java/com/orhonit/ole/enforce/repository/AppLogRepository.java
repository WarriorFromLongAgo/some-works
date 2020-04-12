package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.AppLogEntity;

public interface AppLogRepository extends JpaRepository<AppLogEntity, Integer> {

}
