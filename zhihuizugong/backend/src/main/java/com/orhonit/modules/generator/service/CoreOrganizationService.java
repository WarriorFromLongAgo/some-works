package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOrganizationEntity;

import java.util.Map;

/**
 * 党组织表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:01:54
 */
public interface CoreOrganizationService extends IService<CoreOrganizationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void insertOrganization(CoreOrganizationEntity coreOrganization);
}

