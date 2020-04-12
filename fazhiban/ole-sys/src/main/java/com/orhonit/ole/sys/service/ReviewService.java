package com.orhonit.ole.sys.service;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.sys.dto.ReviewRecordDTO;
import com.orhonit.ole.sys.model.ReviewItemEntity;
import com.orhonit.ole.sys.model.ReviewRecordEntity;
import com.orhonit.ole.sys.model.ReviewRecordItemEntity;

/**
 * 案卷评查
 * @author liuzhih
 *
 */

public interface ReviewService {
	
	/**
	 * 保存案件评查信息
	 * @param reviewDTO
	 */
	void saveReviewRecord(ReviewRecordDTO reviewRecordDTO);
	
	/**
	 * 保存案件评查信息APP
	 * @param reviewDTO
	 */
	void saveReviewRecordByapp(ReviewRecordDTO reviewRecordDTO);
	
	/**
	 * 获取总数
	 * @param params
	 * @return
	 */
	Integer getCount(Map<String, Object> params);
	
	/**
	 * 获取列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<ReviewItemEntity> getList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * 获取评查类别树
	 * @return
	 */
	List<ReviewItemEntity> getAllReviewItem();

	
	/**
	 * 根据父级ID获取下级评查项目
	 * @param id
	 * @param caseId 
	 * @return
	 */
	List<ReviewItemEntity> getItemIdByParentId(Integer id, String caseId);

	
	/**
	 * 评查记录总数
	 * @param params
	 * @return
	 */
	int getReviewRecordCount(Map<String, Object> params);

	/**
	 * 评查记录列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<ReviewRecordDTO> getReviewRecordList(Map<String, Object> params, Integer start, Integer length);
	
	
	/**
	 * 评查记录提交
	 * @param reviewRecordDTO
	 */
	void submitReview(ReviewRecordDTO reviewRecordDTO);
	
	/**
	 * 获取评查记录分数
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	ReviewRecordDTO getReviewRecordByCaseId(String caseId);

	/**
	 * 根据案件编号查询已评查项目详情查询
	 * @param caseId
	 * @return
	 */
	List<ReviewRecordItemEntity> getReviewRecordItemIdByCaseId(String caseId);

	/**
	 *app 根据案件编号查询已评查项目详情查询
	 * @param caseId
	 * @return
	 */
	List<ReviewRecordItemEntity> getAppReviewRecordItemIdByCaseId(String caseId);

	/**
	 * 根据评查记录项目id删除
	 * @param id
	 */
	void  deleteReviewRecordItemIdById(String id);
}
