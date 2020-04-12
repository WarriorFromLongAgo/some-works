package com.orhonit.modules.generator.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppOsExamEntity;
import com.orhonit.modules.generator.app.vo.AppOsExamVO;

/**
 * APP端试卷
 * @author YaoSC
 *
 */
@Mapper
public interface AppOsExamDao extends BaseMapper<AppOsExamEntity> {
	
	/**
	 * 根据试卷编号找试卷信息
	 * @param paperNo
	 * @return
	 */
	AppOsExamEntity selectExamOne(@Param("paperNo")String paperNo);
	
	// 获取当前可以答题的试卷
	List<AppOsExamVO> getExamNow();

}
