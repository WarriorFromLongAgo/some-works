package com.orhonit.modules.generator.app.dao;


import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppVideoLearningRecordEntity;

/**
  * 组工讲堂  学习记录
 * @author YaoSC
 *
 */
@Mapper
public interface AppVideoLearningRecordDao extends BaseMapper<AppVideoLearningRecordEntity>{
	
	/**
	 * 查询用户学习记录
	 * @param identifier
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@Select("SELECT count(record_id) FROM os_course_record WHERE identifier=#{identifier} and user_id=#{userId} and course_id = #{courseId}")
	Integer  js(String identifier,Integer userId,Integer courseId);
	
	/**
	  * 组工讲堂 学习视频
	 * @param userId
	 * @param courseId
	 * @param rememberTime
	 */
	@Insert("INSERT INTO os_course_record (user_id,course_id,remember_time,update_time,create_time,get_integral,identifier,study_status,lower_id,lower_name)VALUES"
			+ "(#{userId},#{courseId},#{rememberTime},#{updateTime},#{createTime},#{getIntegral},#{integral},#{studyStatus},(SELECT lower_id FROM sys_user WHERE user_id=#{userId}),"
			+ "(SELECT lower_name FROM sys_user WHERE user_id=#{userId}))")
	void insertRecord(@Param("userId") Integer userId,@Param("courseId")Integer courseId,
			@Param("rememberTime")Integer rememberTime,@Param("updateTime")Date updateTime,
			@Param("createTime")Date createTime, @Param("getIntegral")Double getIntegral,
			@Param("integral")String integral,@Param("studyStatus")String studyStatus);
	
	
	/**
	  * 组工书苑  学习书籍
	 * @param userId
	 * @param courseId
	 * @param updateTime
	 * @param createTime
	 * @param getIntegral
	 * @param identifier
	 * @param studyStatus
	 */
	@Insert("INSERT INTO os_course_record (user_id,course_id,update_time,create_time,get_integral,identifier,study_status)VALUES"
			+ "(#{userId},#{courseId},#{updateTime},#{createTime},#{getIntegral},#{identifier},#{studyStatus})")
	void insertBookeStore(@Param("userId") Integer userId,@Param("courseId")Integer courseId,
			@Param("updateTime")Date updateTime,@Param("createTime")Date createTime,@Param("getIntegral")Integer getIntegral,
			@Param("identifier")String identifier,@Param("studyStatus")String studyStatus);

	
	/**
	 * 第二次提交学习记录更新时长
	 * @param rememberTime
	 * @param recordId
	 */
	@Update("UPDATE os_course_record SET remember_time=#{rememberTime} WHERE course_id=#{courseId} AND user_id=#{userId}")
	void updateRememberTime(@Param("rememberTime") int rememberTime,@Param("courseId")int courseId,@Param("userId")Integer userId);


}
