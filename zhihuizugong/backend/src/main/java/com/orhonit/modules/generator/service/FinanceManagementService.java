package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.FinanceManagementEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.Map;

/**
 * 财务管理主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:11:59
 */
public interface FinanceManagementService extends IService<FinanceManagementEntity> {

    PageUtils queryPage(Map<String, Object> params);

	FinanceManagementEntity selectFinanceById(String id);

	void save(FinanceManagementEntity financeManagement);
}

