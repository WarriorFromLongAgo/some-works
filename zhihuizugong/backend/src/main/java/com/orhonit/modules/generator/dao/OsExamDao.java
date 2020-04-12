package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.OsExamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 试卷表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-07 18:11:19
 */
@Mapper
public interface OsExamDao extends BaseMapper<OsExamEntity> {
	
	void insertOsExamEntity(OsExamEntity entity);
	
	void deleteOsExam(@Param("examId")String examId);
	
}
