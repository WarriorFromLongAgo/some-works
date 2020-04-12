package com.orhonit.ole.report.service.review.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.report.dao.ReportReviewDao;
import com.orhonit.ole.report.dto.ReviewResultDTO;
import com.orhonit.ole.report.service.review.ReportReviewService;

@Service
public class ReportReviewServiceImpl implements ReportReviewService{

	@Autowired
	private ReportReviewDao reviewDao;

	@Override
	public List<ReviewResultDTO> getReviewResultByScore(Map<String, Object> params) {
		return 	reviewDao.getReviewResultByScore(params);
	}



}
