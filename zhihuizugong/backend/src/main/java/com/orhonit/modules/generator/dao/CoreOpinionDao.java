package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOpinionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


/**
 * 
 * 
 * @author xiaobai
 * @date 2019-05-13 14:37:49
 */
@Mapper
public interface CoreOpinionDao extends BaseMapper<CoreOpinionEntity> {

	void insertOpinion(CoreOpinionEntity coreOpinion);

	@Delete("delete from core_opinion where opinion_id = #{opinionId}")
	void deleteByOpinionId(String opinionId);
}
