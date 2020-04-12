package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.CoreOpenFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 党务公开附件表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-05-22 17:55:44
 */
public interface CoreOpenFileService extends IService<CoreOpenFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CoreOpenFileEntity> getById(String openId);

    void deleteByOpenId(String openId);
}

