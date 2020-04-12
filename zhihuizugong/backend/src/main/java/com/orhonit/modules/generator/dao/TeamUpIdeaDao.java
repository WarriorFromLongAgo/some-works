package com.orhonit.modules.generator.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.TeamUpIdeaEntity;



@Mapper
public interface TeamUpIdeaDao extends BaseMapper<TeamUpIdeaEntity>{
	@Delete("DELETE FROM idea_team_up_ideas WHERE idea_id=#{ideaId}")
	void deleteIdeaById(Integer ideaId);

}
