package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.FinanceFileEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 财务管理附件表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:00
 */
@Mapper
public interface FinanceFileDao extends BaseMapper<FinanceFileEntity> {

	List<FinanceFileEntity> wordList(@Param("financeId")String financeId);

	List<FinanceFileEntity> audioList(@Param("financeId")String financeId);

	List<FinanceFileEntity> imageList(@Param("financeId")String financeId);

	List<FinanceFileEntity> videoList(@Param("financeId")String financeId);

	List<FinanceFileEntity> allList(@Param("financeId")String financeId);
	
}
