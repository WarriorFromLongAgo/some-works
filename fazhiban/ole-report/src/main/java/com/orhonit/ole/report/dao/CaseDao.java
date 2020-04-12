package com.orhonit.ole.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orhonit.ole.report.dto.CaseDetailDTO;

@Mapper
public interface CaseDao {

	List<CaseDetailDTO> findCase(String id);

}
