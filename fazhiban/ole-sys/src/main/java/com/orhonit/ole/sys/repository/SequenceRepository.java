package com.orhonit.ole.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.sys.model.SequenceEntity;

public interface SequenceRepository extends JpaRepository<SequenceEntity, String> {

}
