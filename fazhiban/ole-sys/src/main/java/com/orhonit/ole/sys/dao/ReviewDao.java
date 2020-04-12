package com.orhonit.ole.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.orhonit.ole.sys.dto.ReviewRecordDTO;
import com.orhonit.ole.sys.model.ReviewItemEntity;
import com.orhonit.ole.sys.model.ReviewRecordEntity;
import com.orhonit.ole.sys.model.ReviewRecordItemEntity;

@Mapper
public interface ReviewDao {
	
	Integer getCount(@Param("params")Map<String, Object> params);
	
	List<ReviewItemEntity> getList(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);

	List<ReviewItemEntity> getAllReviewItem();

	
	@Select("select t.id,t.content name,t.score from ole_ef_review_item t where t.parent_id = #{id}")
	List<ReviewItemEntity> getItemIdByParentId(Integer id);

	
	@Select("SELECT  t.id,t.record_id recordId,t.item_id itemId,t.is_top isTop,k.score,k.remark,k.status,k.create_by createBy  FROM  ole_ef_review_record_item t  LEFT JOIN ole_ef_review_record k ON t.record_id = k.id where k.case_id= #{caseId}")
	List<ReviewRecordItemEntity> getRrcordItemIdByCaseId(String caseId);

	@Delete("DELETE FROM ole_ef_review_record_item where record_id in (select id from  ole_ef_review_record k where k.case_id=#{caseId})")
	void deleteRecordItemByCaseId(String caseId);

	@Delete("DELETE from  ole_ef_review_record  where case_id=#{caseId}")
	void deleteRecordByCaseId(String caseId);

	List<ReviewRecordDTO> getReviewRecordList(@Param("params")Map<String, Object> params, @Param("start")Integer start, @Param("length")Integer length);

	int getReviewRecordCount(@Param("params")Map<String, Object> params);

	
	@Select("SELECT  t.id,t.record_id recordId,t.item_id itemId,t.is_top isTop,r.score,k.remark,k.status,r.name itemName,r.content itemContent,eri.id parentItemId,eri.name parentItemName,eri.content parentItemContent  FROM  ole_ef_review_record_item t  LEFT JOIN ole_ef_review_record k ON t.record_id = k.id  LEFT JOIN ole_ef_review_item r ON t.item_id = r.id  left join ole_ef_review_item eri ON eri.id=r.parent_id  where k.case_id= #{caseId}")
	List<ReviewRecordItemEntity> getAppReviewRecordItemIdByCaseId(String caseId);

	@Select("select *  from  ole_ef_review_record  where case_id=#{caseId}")
	List<ReviewRecordEntity> getReviewRecordByCaseId(String caseId);
	
}
