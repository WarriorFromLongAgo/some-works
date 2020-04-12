package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOpinionEntity;

import java.util.Map;

/**
 * 
 *
 * @author xiaobai
 * @date 2019-05-13 14:37:49
 */
public interface CoreOpinionService extends IService<CoreOpinionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void insertOpinion(CoreOpinionEntity coreOpinion);

    void deleteByOpinionId(String opinionId);
}

