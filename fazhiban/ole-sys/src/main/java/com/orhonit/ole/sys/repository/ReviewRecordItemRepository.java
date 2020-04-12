package com.orhonit.ole.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.sys.model.ReviewRecordItemEntity;

public interface ReviewRecordItemRepository extends JpaRepository<ReviewRecordItemEntity, Integer> {
	
	public ReviewRecordItemEntity findOneById(Integer id);
}
