package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppOsExamQuestionEntity;



@Mapper
public interface AppOsExamQuestionDao extends BaseMapper<AppOsExamQuestionEntity>{
	
	
	void insertOsExamQuestion(AppOsExamQuestionEntity entity);
	
	/**
	 * 更新最新sl条答题试卷编号
	 * @param paperNo
	 * @param sl
	 */
	@Update("update os_exam_question set paper_no=#{paperNo} ORDER BY create_time  desc LIMIT #{sl}")
	void updatePaPerNo(@Param("paperNo")String paperNo,@Param("sl")int sl);
	/**
	 *  查找试卷的答案
	 * @param paperNo
	 * @param userId
	 * @return
	 */
	List<AppOsExamQuestionEntity> selectByPaperNo(@Param("paperNo")String paperNo,@Param("userId")String userId);
	
	/**
	 *  在线考试查找是否有试题
	 * @param examId
	 * @param userId
	 * @return
	 */
	List<AppOsExamQuestionEntity> selectOnlyOnlineExam(@Param("examId")String examId,@Param("userId")String userId);
	/**
	 *  删除正式考试的答案
	 * @param examId
	 * @param userId
	 */
    void deleteOnlyOnlineExamQuestion(@Param("examId")String examId,@Param("userId")String userId);
	
	

}
