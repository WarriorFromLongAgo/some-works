package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.OsQuestionEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 问题表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 09:34:14
 */
@Mapper
public interface OsQuestionDao extends BaseMapper<OsQuestionEntity> {
	
	
	
	/**
	 * 修改试题管理的列题
	 * @param questionNo
	 */
	@Update("update os_question  set  "
			+ "question_a = #{questionA},"
			+ "question_b=#{questionB},"
			+ "question_c=#{questionC}, "
			+ "question_d=#{questionD},"
			+ "answer=#{answer},"
			+ "answer_desc=#{answerDesc},"
			+ "update_time=#{updateTime},"
			+ "level=#{level} "
			+ "where question_no=#{questionNo}")
	void updateQuestion(String questionNo );
	
	
   /**
	 * 查询题库下的例题
	 * @param LibraryId
	 * @return
	 */
	List<OsQuestionEntity> selectByLibraryId(@Param("LibraryId")String LibraryId);
	
	
	void insertOsQuestion(OsQuestionEntity osuestionentity);
	
	//试题删除
	//@Delete("delete from os_question where question_no = #{questionNo}")
	void deleteOsQuetion(@Param("questionNo")String questionNo);
	
}
