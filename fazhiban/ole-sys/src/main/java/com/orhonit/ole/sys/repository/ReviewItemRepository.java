package com.orhonit.ole.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.sys.model.ReviewItemEntity;

public interface ReviewItemRepository extends JpaRepository<ReviewItemEntity, Integer> {
	
	public ReviewItemEntity findOneById(Integer id);
}
