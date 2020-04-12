package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreWorkEntity;

import java.util.Map;

/**
 * 民心连心桥表
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
public interface CoreWorkService extends IService<CoreWorkEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    boolean save(CoreWorkEntity entity);

    CoreWorkEntity selectByServeId(String serveId);

    void deleteByServeId(String serveId);
    
}

