package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.DocumentContentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 09:56:34
 */
public interface DocumentContentService extends IService<DocumentContentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DocumentContentEntity> getById(String documentId);
}

