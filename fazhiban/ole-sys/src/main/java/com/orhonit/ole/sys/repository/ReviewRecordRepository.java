package com.orhonit.ole.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.sys.model.ReviewRecordEntity;

public interface ReviewRecordRepository extends JpaRepository<ReviewRecordEntity, Integer> {
	
	public ReviewRecordEntity findOneById(Integer id);
}
