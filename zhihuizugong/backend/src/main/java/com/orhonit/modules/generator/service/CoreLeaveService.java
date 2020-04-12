package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreLeaveEntity;

import java.util.Map;

/**
 * @author xiaobai
 * @date 2019-05-10 15:14:54
 */
public interface CoreLeaveService extends IService<CoreLeaveEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
}

