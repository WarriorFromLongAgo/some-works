package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.FinancePeopleEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 财务管理人员表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:02
 */
@Mapper
public interface FinancePeopleDao extends BaseMapper<FinancePeopleEntity> {

	@Delete("delete FROM tb_finance_people where user_id = #{userId} and finance_id = #{financeId}")
	void deletePeople(@Param("userId")Integer userId,@Param("financeId") String financeId);

	List<FinancePeopleEntity> allList(@Param("financeId")String financeId);

	List<FinancePeopleEntity> lowerList(@Param("lowerId")Integer lowerId);
	@Delete("delete from tb_finance_people where finance_id = #{financeId}")
	void deletePeople(@Param("financeId")String financeId);
	
}
