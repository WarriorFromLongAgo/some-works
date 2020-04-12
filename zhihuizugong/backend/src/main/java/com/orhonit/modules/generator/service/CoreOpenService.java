package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOpenEntity;

import java.util.Map;

/**
 * 党务公开表
 *
 * @author xiaobai
 * @date 2019-05-18 15:14:02
 */
public interface CoreOpenService extends IService<CoreOpenEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void insertOpen(CoreOpenEntity coreOpen);
}

