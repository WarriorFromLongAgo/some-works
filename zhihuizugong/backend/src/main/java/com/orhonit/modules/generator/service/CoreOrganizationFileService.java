package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOrganizationFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 党组织附件表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:14:55
 */
public interface CoreOrganizationFileService extends IService<CoreOrganizationFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CoreOrganizationFileEntity> getById(String organizationId);
}

