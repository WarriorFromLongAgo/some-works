package com.orhonit.ole.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.sys.model.AreaEntity;

public interface AreaRepository extends JpaRepository<AreaEntity, String> {
	
	public AreaEntity findOneById(Integer id);
	
//	public List
}
