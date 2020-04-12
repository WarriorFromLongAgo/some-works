package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppOsQuestionEntity;


/**
 *  APP端问题表
 * @author YaoSC
 *
 */
@Mapper
public interface AppOsQuestionDao extends BaseMapper<AppOsQuestionEntity>{
	
	
	/**
	 * 根据题库编号和题数随机出题
	 * @param LibraryNo 题库编号
	 * @param Num  题数
	 * @return 试卷题
	 */
	List<AppOsQuestionEntity> selectQuestionByNumWhereLibraryNO(@Param("LibraryNo")String LibraryNo,@Param("rand")Boolean rand ,@Param ("Num") Integer Num);

}
