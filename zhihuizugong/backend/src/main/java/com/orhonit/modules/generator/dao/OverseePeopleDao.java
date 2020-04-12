package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.OverseePeopleEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 领导督办人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@Mapper
public interface OverseePeopleDao extends BaseMapper<OverseePeopleEntity> {

	@Delete("delete FROM tb_oversee_people where user_id = #{userId} and oversee_id = #{overseeId}")
	void deletePeople(@Param("userId")Integer userId,@Param("overseeId") String overseeId);

	List<OverseePeopleEntity> allList(@Param("overseeId")String overseeId);
	
	List<OverseePeopleEntity> selectPeople(@Param("userId")Long userId,@Param("overseeId") String overseeId);
	
}
