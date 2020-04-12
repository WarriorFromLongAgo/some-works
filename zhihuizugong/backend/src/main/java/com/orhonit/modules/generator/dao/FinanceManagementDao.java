package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.FinanceManagementEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 财务管理主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:11:59
 */
@Mapper
public interface FinanceManagementDao extends BaseMapper<FinanceManagementEntity> {

	FinanceManagementEntity selectFinanceById(String id);

	void save(FinanceManagementEntity financeManagement);
	
}
