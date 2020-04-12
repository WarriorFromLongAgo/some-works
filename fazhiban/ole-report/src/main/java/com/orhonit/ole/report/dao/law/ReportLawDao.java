package com.orhonit.ole.report.dao.law;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orhonit.ole.report.dto.check.ReportLawDTO;

@Mapper
public interface ReportLawDao {
	
	//获取各类型的法律数量
	List<ReportLawDTO> getLawItemCount();
	
	//获取法律的总数
	List<ReportLawDTO> getCount();
	
}
