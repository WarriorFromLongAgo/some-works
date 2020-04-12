package com.orhonit.modules.generator.app.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppOsErrorQuestionEntity;


/**
  * 错题记录
 * @author YaoSC
 *
 */
@Mapper
public interface AppOsErrorQuestionDao extends BaseMapper<AppOsErrorQuestionEntity>{
	
	/**
	 * 记录错题
	 * @param entity
	 */
	@Insert("INSERT INTO os_error_question (id, question_no, error_answer, user_id, create_time,update_time )\r\n" + 
			"VALUES\r\n" + 
			"	( #{id},#{questionNo},#{errorAnswer},#{userId},#{createTime},#{updateTime})")
	void InsertOsErrorQuestion(AppOsErrorQuestionEntity entity);

}
