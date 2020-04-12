package com.orhonit.ole.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.sys.model.SysDictEntity;

public interface SysDictRepository extends JpaRepository<SysDictEntity, String>{

	List<SysDictEntity> findByTypeValueOrderBySortAsc(String typeValue);

}
