package com.orhonit.ole.report.dao.basics;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orhonit.ole.report.dto.basics.ReportBasicsDTO;

@Mapper
public interface ReportBasicsDao {
	
	List<ReportBasicsDTO> getCheckedDayItem();
	
	List<ReportBasicsDTO> getCheckedSpecialItem();
	
}
