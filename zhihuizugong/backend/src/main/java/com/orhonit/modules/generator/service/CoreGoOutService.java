package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreGoOutEntity;

import java.util.Map;

/**
 * 外出登记表
 * @author xiaobai
 * @date 2019-05-10 13:48:31
 */
public interface CoreGoOutService extends IService<CoreGoOutEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
}

