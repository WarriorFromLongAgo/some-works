package com.orhonit.ole.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orhonit.ole.report.dto.LawYearDTO;
@Mapper
public interface LawYearDao {
	//新增法律法规年度统计 
	List<LawYearDTO> selectLaw();
	//新增法律法规名称
	List<LawYearDTO> selectLawName();
	
}
