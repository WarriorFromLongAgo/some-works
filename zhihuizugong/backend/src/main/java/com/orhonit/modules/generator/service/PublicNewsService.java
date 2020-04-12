package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.PublicNewsEntity;

import java.util.Map;

/**
 * 智慧e家人才家园
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-14 14:41:18
 */
public interface PublicNewsService extends IService<PublicNewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

