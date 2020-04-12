package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreWorkDynamicEntity;

import java.util.Map;

/**
 * 工作队动态表
 *
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
public interface CoreWorkDynamicService extends IService<CoreWorkDynamicEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    void insertDynamic(CoreWorkDynamicEntity coreWorkDynamic);

    void  deleteByDynamicId(String dynamicId);
    
}

