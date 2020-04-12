package com.orhonit.ole.enforce.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orhonit.ole.enforce.entity.DeptEntity;

@Mapper
public interface DeptRepository extends JpaRepository<DeptEntity, String>{

}
