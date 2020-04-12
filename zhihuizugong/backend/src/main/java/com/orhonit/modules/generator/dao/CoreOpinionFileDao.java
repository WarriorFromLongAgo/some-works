package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOpinionFileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 意见附件
 * 
 * @author xiaobai
 * @date 2019-05-13 14:37:50
 */
@Mapper
public interface CoreOpinionFileDao extends BaseMapper<CoreOpinionFileEntity> {

	@Select("select * from core_opinion_file where opinion_id = #{opinionId}")
	List<CoreOpinionFileEntity> getById(String opinionId);

	@Delete("DELETE FROM `core_opinion_file` WHERE opinion_id = #{opinionId}")
	void removeById(String opinionId);
	
}
