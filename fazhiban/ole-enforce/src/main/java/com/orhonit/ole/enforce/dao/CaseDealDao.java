package com.orhonit.ole.enforce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.entity.CaseDealEntity;

@Mapper
public interface CaseDealDao {
	
	List<CaseDealEntity> getCaseDealByCaseId(@Param("caseId") String caseId);

}
