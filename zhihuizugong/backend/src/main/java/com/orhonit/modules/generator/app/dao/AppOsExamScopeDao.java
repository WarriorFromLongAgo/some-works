package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppOsExamScopeEntity;
import com.orhonit.modules.generator.app.vo.AppPersonRrankVO;

/**
 * 答题成绩表
 * @author YaoSC
 *
 */
@Mapper
public interface AppOsExamScopeDao extends BaseMapper<AppOsExamScopeEntity>{
	
	/**
	 * 正式考试成绩  每张卷只能有一个成绩
	 * @return
	 */
	AppOsExamScopeEntity selectOnlyOnlieExamScope(@Param("examId")String examId,@Param("userId")String userId);
	
	/**
	 * 个人排行榜
	 * @return
	 */
	List<AppPersonRrankVO> selectPersonRank(@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	
	void insertExamScope(AppOsExamScopeEntity appOsExamScopeEntity);

}
