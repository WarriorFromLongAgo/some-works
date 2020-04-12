package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreWorkDynamicFileEntity;

import java.util.Map;

/**
 * 动态附件表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
public interface CoreWorkDynamicFileService extends IService<CoreWorkDynamicFileEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void removeById(String dynamicId);
}

