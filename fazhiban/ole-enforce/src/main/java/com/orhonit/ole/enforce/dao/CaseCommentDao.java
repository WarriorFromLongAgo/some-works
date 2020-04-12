package com.orhonit.ole.enforce.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhonit.ole.enforce.entity.CaseCommentEntity;


@Mapper
public interface CaseCommentDao {
	
	@Insert("insert into ole_ef_case_comment(case_id,comment,create_name,create_by,create_date) values (#{caseId}, #{comment}, #{createName}, #{createBy}, #{createDate})")
	int save(CaseCommentEntity caseCommentEntity);

	List<CaseCommentEntity> caseCommentList(@Param("caseId")String caseId);
}
