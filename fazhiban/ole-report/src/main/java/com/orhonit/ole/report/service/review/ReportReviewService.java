package com.orhonit.ole.report.service.review;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.report.dto.ReviewResultDTO;

/**
 * 案件评查
 */
public interface ReportReviewService {

	List<ReviewResultDTO> getReviewResultByScore(Map<String, Object> params);

}
