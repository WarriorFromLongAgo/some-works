package com.orhonit.ole.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.report.dto.ReviewResultDTO;

@Mapper
public interface ReportReviewDao {

	List<ReviewResultDTO> getReviewResultByScore(@Param("params")Map<String, Object> params);

}
