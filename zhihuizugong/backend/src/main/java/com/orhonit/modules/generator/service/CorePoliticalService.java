package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CorePoliticalEntity;

import java.util.Map;

/**
 * 生活时时讲
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 16:36:25
 */
public interface CorePoliticalService extends IService<CorePoliticalEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void batchDeletePolitical(Integer[] ids);
}

