package com.orhonit.ole.enforce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.DocFlowEntity;

public interface DocFlowRepository extends JpaRepository<DocFlowEntity, String> , JpaSpecificationExecutor<DocFlowEntity>{

}
