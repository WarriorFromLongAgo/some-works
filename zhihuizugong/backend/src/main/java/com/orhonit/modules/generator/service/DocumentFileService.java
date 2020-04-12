package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.DocumentFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 公文管理表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-06 15:13:07
 */
public interface DocumentFileService extends IService<DocumentFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DocumentFileEntity> getById(String documentId);
}

