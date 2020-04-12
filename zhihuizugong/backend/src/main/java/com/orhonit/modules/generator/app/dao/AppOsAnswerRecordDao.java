package com.orhonit.modules.generator.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.entity.AppOsAnswerRecordEntity;



/*
 * 学员答题记录表
 */
@Mapper
public interface AppOsAnswerRecordDao extends BaseMapper<AppOsAnswerRecordEntity>{
	
	
	//插入学员答题记录
	void inserAppOsAnswerRecord(AppOsAnswerRecordEntity appOsAnswerRecordEntity);

}
